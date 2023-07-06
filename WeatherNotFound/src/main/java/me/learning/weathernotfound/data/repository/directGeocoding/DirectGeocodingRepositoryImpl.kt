package me.learning.weathernotfound.data.repository.directGeocoding

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import me.learning.weathernotfound.data.local.dao.DirectGeocodingDao
import me.learning.weathernotfound.data.network.providers.RequestProvider
import me.learning.weathernotfound.data.network.providers.UrlProvider
import me.learning.weathernotfound.data.repository.Failure
import me.learning.weathernotfound.data.repository.Response
import me.learning.weathernotfound.data.repository.ResponseType
import me.learning.weathernotfound.data.repository.Success
import me.learning.weathernotfound.data.repository.WeatherNotFoundError
import me.learning.weathernotfound.data.repository.WeatherNotFoundResponse
import me.learning.weathernotfound.domain.directGeocoding.Converters
import me.learning.weathernotfound.domain.directGeocoding.databaseModels.DirectGeocodingEntity
import me.learning.weathernotfound.domain.directGeocoding.networkModels.DirectGeocodingResponse
import me.learning.weathernotfound.domain.directGeocoding.presentationModels.DirectGeocodingModel
import me.learning.weathernotfound.utils.Utilities.threeDayPassed
import okhttp3.OkHttpClient

internal class DirectGeocodingRepositoryImpl constructor(
    private val directGeocodingDao: DirectGeocodingDao,
    private val okHttpClient: OkHttpClient,
    private val gsonConverter: Gson,
) : DirectGeocodingRepository {

    private lateinit var fetchCityNameCoordinatesInformationJob: Job
    private lateinit var invalidateCityNameCoordinateInformationCacheJob: Job
    private lateinit var removeCacheInformationJob: Job

    override fun getCityNameCoordinatesInformation(
        cityName: String,
        limit: Int,
        resultInvoker: (Response<WeatherNotFoundResponse<DirectGeocodingModel>, WeatherNotFoundError>) -> Unit
    ) {
        fetchCityNameCoordinatesInformationJob = CoroutineScope(Dispatchers.IO).launch {
            val cacheResponse = directGeocodingDao.getDirectGeocodingByCoordinateName(
                coordinateName = cityName
            )

            if (cacheResponse.isNotEmpty()) {
                resultInvoker.invoke(
                    Success(
                        WeatherNotFoundResponse(
                            responseType = ResponseType.CACHE,
                            responseModel = Converters.directGeocodingEntitiesToDirectGeocodingModel(
                                entities = cacheResponse
                            )
                        )
                    )
                )

                if (cacheResponse[0].updatedAt.threeDayPassed() /* For now we just check one of them... */) {
                    // Update cached Information
                    startNetworkRequest(
                        coordinateName = cityName,
                        limit = limit,
                        responseCallback = { /* Do nothing */ },
                        responseReceivedCallback = { responseModel ->
                            cacheResponseModelIntoDatabase(
                                lastDirectInformationEntity = cacheResponse,
                                directGeocodingResponse = responseModel
                            )
                        }
                    )
                }
            } else {
                startNetworkRequest(
                    coordinateName = cityName,
                    limit = limit,
                    responseCallback = resultInvoker,
                    responseReceivedCallback = { responseModel ->
                        cacheResponseModelIntoDatabase(
                            lastDirectInformationEntity = null,
                            directGeocodingResponse = responseModel
                        )
                    }
                )
            }
        }
    }

    override fun removeCacheInformationOlderThen(timeStamp: Long) {
        removeCacheInformationJob = CoroutineScope(Dispatchers.IO).launch {
            directGeocodingDao.deleteDirectGeocodingEntitiesOlderThan(selectedTimeStamp = timeStamp)
        }
    }

    override fun invalidateCache() {
        invalidateCityNameCoordinateInformationCacheJob = CoroutineScope(Dispatchers.IO).launch {
            directGeocodingDao.invalidateCache()
        }
    }

    override fun dispose() {
        if (this::fetchCityNameCoordinatesInformationJob.isInitialized
            && !fetchCityNameCoordinatesInformationJob.isCompleted
            && !fetchCityNameCoordinatesInformationJob.isCancelled
        ) {
            fetchCityNameCoordinatesInformationJob.cancel()
        }

        if (this::invalidateCityNameCoordinateInformationCacheJob.isInitialized
            && !invalidateCityNameCoordinateInformationCacheJob.isCompleted
            && !invalidateCityNameCoordinateInformationCacheJob.isCancelled
        ) {
            invalidateCityNameCoordinateInformationCacheJob.cancel()
        }

        if (this::removeCacheInformationJob.isInitialized
            && !removeCacheInformationJob.isCompleted
            && !removeCacheInformationJob.isCancelled
        ) {
            removeCacheInformationJob.cancel()
        }
    }

    private suspend fun startNetworkRequest(
        coordinateName: String,
        limit: Int,
        responseCallback: (Response<WeatherNotFoundResponse<DirectGeocodingModel>, WeatherNotFoundError>) -> Unit,
        responseReceivedCallback: suspend (directGeocodingResponse: DirectGeocodingResponse) -> Unit
    ) {
        val request = RequestProvider.provideDirectGeocodingRequest(
            url = UrlProvider.DIRECT_GEOCODING_URL,
            coordinateName = coordinateName,
            limit = limit
        ) ?: throw IllegalStateException("Request is null! Check directGeocoding request!")

        try {
            val response = okHttpClient.newCall(request).execute()
            if (response.isSuccessful && response.body != null) {
                val responseModel: DirectGeocodingResponse

                try {
                    responseModel = gsonConverter.fromJson(
                        response.body!!.toString(),
                        DirectGeocodingResponse::class.java
                    )
                } catch (jsonSyntaxException: JsonSyntaxException) {
                    responseCallback.invoke(
                        Failure(
                            WeatherNotFoundError(
                                exception = jsonSyntaxException,
                                internalErrorMessage = "Failed to parse response into DirectGeocodingResponse!"
                            )
                        )
                    )
                    return
                }

                responseCallback.invoke(
                    Success(
                        WeatherNotFoundResponse(
                            httpResponseCode = response.code,
                            responseType = ResponseType.NETWORK,
                            responseModel = Converters.directGeocodingResponseToDirectGeocodingModel(
                                response = responseModel
                            )
                        )
                    )
                )

                // Notify callback to save the response into database
                responseReceivedCallback.invoke(responseModel)
            } else {
                responseCallback.invoke(
                    Failure(
                        WeatherNotFoundError(
                            httpResponseCode = response.code,
                            httpResponseMessage = response.message,
                        )
                    )
                )
            }
        } catch (exception: Exception) {
            responseCallback.invoke(
                Failure(WeatherNotFoundError(exception = exception))
            )
        }
    }

    // TODO: Performance improving needed!
    private suspend fun cacheResponseModelIntoDatabase(
        lastDirectInformationEntity: List<DirectGeocodingEntity>?,
        directGeocodingResponse: DirectGeocodingResponse,
    ) {
        lastDirectInformationEntity?.let {
            directGeocodingDao.deleteDirectGeocodingEntity(lastDirectInformationEntity)
        }
        val finalEntityModel = Converters.directGeocodingResponseToDirectGeocodingEntity(
            response = directGeocodingResponse
        )
        directGeocodingDao.insertDirectGeocodingEntity(finalEntityModel)
    }
}
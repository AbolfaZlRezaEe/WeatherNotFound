package me.learning.weathernotfound.data.repository.reverseGeocoding

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import me.learning.weathernotfound.data.local.dao.ReverseGeocodingDao
import me.learning.weathernotfound.data.network.providers.RequestProvider
import me.learning.weathernotfound.data.network.providers.UrlProvider
import me.learning.weathernotfound.data.repository.Failure
import me.learning.weathernotfound.data.repository.Response
import me.learning.weathernotfound.data.repository.ResponseType
import me.learning.weathernotfound.data.repository.Success
import me.learning.weathernotfound.data.repository.WeatherNotFoundError
import me.learning.weathernotfound.data.repository.WeatherNotFoundResponse
import me.learning.weathernotfound.domain.reverseGeocoding.Converters
import me.learning.weathernotfound.domain.reverseGeocoding.databaseModels.ReverseGeocodingEntity
import me.learning.weathernotfound.domain.reverseGeocoding.networkModels.ReversGeocodingResponse
import me.learning.weathernotfound.domain.reverseGeocoding.presentationModels.LocationInfoModel
import me.learning.weathernotfound.utils.Utilities.threeDayPassed
import okhttp3.OkHttpClient

internal class ReverseGeocodingRepositoryImpl(
    private val reverseGeocodingDao: ReverseGeocodingDao,
    private val okHttpClient: OkHttpClient,
    private val gsonConverter: Gson
) : ReverseGeocodingRepository {

    private lateinit var fetchCoordinatesInformationJob: Job
    private lateinit var invalidateCoordinateInformationCacheJob: Job
    private lateinit var removeCacheInformationJob: Job

    override fun getCoordinateInformation(
        latitude: Double,
        longitude: Double,
        limit: Int,
        resultInvoker: (Response<WeatherNotFoundResponse<LocationInfoModel>, WeatherNotFoundError>) -> Unit
    ) {
        fetchCoordinatesInformationJob = CoroutineScope(Dispatchers.IO).launch {
            val cacheResponse = reverseGeocodingDao.getReverseGeocodingByCoordinates(
                latitude = latitude,
                longitude = longitude
            )

            if (cacheResponse != null) {
                resultInvoker.invoke(
                    Success(
                        WeatherNotFoundResponse(
                            responseType = ResponseType.CACHE,
                            responseModel = Converters.reverseGeocodingEntityToLocationInfoModel(
                                entity = cacheResponse
                            )
                        )
                    )
                )

                if (cacheResponse.updatedAt.threeDayPassed()) {
                    // Update cached Information
                    startNetworkRequest(
                        latitude = latitude,
                        longitude = longitude,
                        limit = limit,
                        responseCallback = { /* Do nothing */ },
                        responseReceivedCallback = { responseModel ->
                            cacheResponseModelIntoDatabase(
                                lastReversInformationEntity = cacheResponse,
                                reverseGeocodingResponse = responseModel
                            )
                        }
                    )
                }
            } else {
                startNetworkRequest(
                    latitude = latitude,
                    longitude = longitude,
                    limit = limit,
                    responseCallback = resultInvoker,
                    responseReceivedCallback = { responseModel ->
                        cacheResponseModelIntoDatabase(
                            lastReversInformationEntity = null,
                            reverseGeocodingResponse = responseModel
                        )
                    }
                )
            }
        }
    }

    override fun removeCacheInformationOlderThan(timeStamp: Long) {
        removeCacheInformationJob = CoroutineScope(Dispatchers.IO).launch {
            reverseGeocodingDao.deleteReverseGeocodingEntitiesOlderThan(selectedTimeStamp = timeStamp)
        }
    }

    override fun invalidateCache() {
        invalidateCoordinateInformationCacheJob = CoroutineScope(Dispatchers.IO).launch {
            reverseGeocodingDao.invalidateCache()
        }
    }

    override fun dispose() {
        if (this::fetchCoordinatesInformationJob.isInitialized
            && !fetchCoordinatesInformationJob.isCompleted
            && !fetchCoordinatesInformationJob.isCancelled
        ) {
            fetchCoordinatesInformationJob.cancel()
        }

        if (this::invalidateCoordinateInformationCacheJob.isInitialized
            && !invalidateCoordinateInformationCacheJob.isCompleted
            && !invalidateCoordinateInformationCacheJob.isCancelled
        ) {
            invalidateCoordinateInformationCacheJob.cancel()
        }

        if (this::removeCacheInformationJob.isInitialized
            && !removeCacheInformationJob.isCompleted
            && !removeCacheInformationJob.isCancelled
        ) {
            removeCacheInformationJob.cancel()
        }
    }

    private suspend fun startNetworkRequest(
        latitude: Double,
        longitude: Double,
        limit: Int,
        responseCallback: (Response<WeatherNotFoundResponse<LocationInfoModel>, WeatherNotFoundError>) -> Unit,
        responseReceivedCallback: suspend (reverseGeocodingResponse: ReversGeocodingResponse) -> Unit
    ) {
        val request = RequestProvider.provideReverseGeocodingRequest(
            url = UrlProvider.REVERSE_GEOCODING_URL,
            latitude = latitude,
            longitude = longitude,
            limit = limit
        ) ?: throw IllegalStateException("Request is null! Check reverseGeocoding request!")

        try {
            val response = okHttpClient.newCall(request).execute()
            if (response.isSuccessful && response.body != null) {
                val responseModel: ReversGeocodingResponse

                try {
                    responseModel = gsonConverter.fromJson(
                        response.body!!.toString(),
                        ReversGeocodingResponse::class.java
                    )
                } catch (jsonSyntaxException: JsonSyntaxException) {
                    responseCallback.invoke(
                        Failure(
                            WeatherNotFoundError(
                                exception = jsonSyntaxException,
                                internalErrorMessage = "Failed to parse response into CurrentWeatherModel!"
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
                            responseModel = Converters.reverseGeocodingResponseToLocationInfoModel(
                                responseModel
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

    private suspend fun cacheResponseModelIntoDatabase(
        lastReversInformationEntity: ReverseGeocodingEntity?,
        reverseGeocodingResponse: ReversGeocodingResponse,
    ) {
        val finalEntityModel =
            Converters.reverseGeocodingResponseToReverseGeocodingEntity(
                response = reverseGeocodingResponse,
                entityId = lastReversInformationEntity?.entityId
            )
        if (lastReversInformationEntity == null) {
            reverseGeocodingDao.insertReverseGeocodingEntity(finalEntityModel)
        } else {
            reverseGeocodingDao.updateReverseGeocodingEntity(finalEntityModel)
        }
    }
}
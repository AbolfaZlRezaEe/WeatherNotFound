package me.learning.weathernotfound.data.repository.reverseGeocoding

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import me.learning.weathernotfound.data.local.LocalInterfaceProvider
import me.learning.weathernotfound.data.local.dao.ReverseGeocodingDao
import me.learning.weathernotfound.data.network.providers.RequestProvider
import me.learning.weathernotfound.data.network.providers.UrlProvider
import me.learning.weathernotfound.data.repository.Failure
import me.learning.weathernotfound.data.repository.Response
import me.learning.weathernotfound.data.repository.Success
import me.learning.weathernotfound.domain.reverseGeocoding.Converters
import me.learning.weathernotfound.domain.reverseGeocoding.databaseModels.ReverseGeocodingEntity
import me.learning.weathernotfound.domain.reverseGeocoding.networkModels.ReversGeocodingResponse
import me.learning.weathernotfound.domain.reverseGeocoding.presentationModels.ReverseGeocodingModel
import me.learning.weathernotfound.presentation.ResponseType
import me.learning.weathernotfound.presentation.WeatherNotFoundError
import me.learning.weathernotfound.presentation.WeatherNotFoundResponse
import me.learning.weathernotfound.utils.Utilities.threeDayPassed
import okhttp3.OkHttpClient

internal class ReverseGeocodingRepositoryImpl(
    private val reverseGeocodingDao: ReverseGeocodingDao?,
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
        resultInvoker: (Response<WeatherNotFoundResponse<ReverseGeocodingModel>, WeatherNotFoundError>) -> Unit
    ) {
        fetchCoordinatesInformationJob = CoroutineScope(Dispatchers.IO).launch {
            if (LocalInterfaceProvider.isCacheMechanismEnabled()) {
                val cacheResponse = reverseGeocodingDao!!.getReverseGeocodingByCoordinates(
                    latitude = latitude,
                    longitude = longitude
                )

                if (cacheResponse.isNotEmpty()) {
                    resultInvoker.invoke(
                        Success(
                            WeatherNotFoundResponse(
                                responseType = ResponseType.CACHE,
                                responseModel = Converters.reverseGeocodingEntitiesToReverseGeocodingModel(
                                    entities = cacheResponse
                                )
                            )
                        )
                    )

                    if (cacheResponse[0].updatedAt.threeDayPassed() /* For now we just check one of them... */) {
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
            } else {
                startNetworkRequest(
                    latitude = latitude,
                    longitude = longitude,
                    limit = limit,
                    responseCallback = resultInvoker,
                    responseReceivedCallback = { /* Do nothing */ }
                )
            }
        }
    }

    override fun removeCacheInformationOlderThan(timeStamp: Long) {
        if (LocalInterfaceProvider.isCacheMechanismDisabled()) return
        removeCacheInformationJob = CoroutineScope(Dispatchers.IO).launch {
            reverseGeocodingDao!!.deleteReverseGeocodingEntitiesOlderThan(selectedTimeStamp = timeStamp)
        }
    }

    override fun invalidateCache() {
        if (LocalInterfaceProvider.isCacheMechanismDisabled()) return
        invalidateCoordinateInformationCacheJob = CoroutineScope(Dispatchers.IO).launch {
            reverseGeocodingDao!!.invalidateCache()
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
        responseCallback: (Response<WeatherNotFoundResponse<ReverseGeocodingModel>, WeatherNotFoundError>) -> Unit,
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
                                internalErrorMessage = "Failed to parse response into LocationInfoModel!"
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

    // TODO: Performance improving needed!
    private suspend fun cacheResponseModelIntoDatabase(
        lastReversInformationEntity: List<ReverseGeocodingEntity>?,
        reverseGeocodingResponse: ReversGeocodingResponse,
    ) {
        lastReversInformationEntity?.let {
            reverseGeocodingDao!!.deleteReverseGeocodingEntities(lastReversInformationEntity)
        }
        val finalEntityModels = Converters.reverseGeocodingResponseToReverseGeocodingEntity(
            response = reverseGeocodingResponse
        )

        reverseGeocodingDao!!.insertReverseGeocodingEntities(finalEntityModels)
    }
}
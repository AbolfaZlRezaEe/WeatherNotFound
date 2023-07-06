package me.learning.weathernotfound.data.repository.currentWeather

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import me.learning.weathernotfound.data.local.LocalInterfaceProvider
import me.learning.weathernotfound.data.local.dao.CurrentWeatherDao
import me.learning.weathernotfound.data.network.providers.NetworkInterfaceProvider
import me.learning.weathernotfound.data.network.providers.RequestProvider
import me.learning.weathernotfound.data.network.providers.UrlProvider
import me.learning.weathernotfound.data.repository.Failure
import me.learning.weathernotfound.data.repository.Response
import me.learning.weathernotfound.data.repository.Success
import me.learning.weathernotfound.data.repository.ifNotSuccessful
import me.learning.weathernotfound.data.repository.ifSuccessful
import me.learning.weathernotfound.domain.currentWeather.Converters
import me.learning.weathernotfound.domain.currentWeather.databaseModels.CurrentWeatherEntity
import me.learning.weathernotfound.domain.currentWeather.networkModels.CurrentWeatherResponseModel
import me.learning.weathernotfound.domain.currentWeather.presentationModels.CurrentWeatherModel
import me.learning.weathernotfound.presentation.ResponseType
import me.learning.weathernotfound.presentation.WeatherNotFoundError
import me.learning.weathernotfound.presentation.WeatherNotFoundResponse
import me.learning.weathernotfound.utils.Utilities.halfDayPassed
import okhttp3.OkHttpClient

internal class CurrentWeatherRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao?,
    private val okHttpClient: OkHttpClient,
    private val gsonConverter: Gson,
) : CurrentWeatherRepository {

    private lateinit var validateOpenWeatherApiKeyJob: Job
    private lateinit var fetchCurrentWeatherInformationJob: Job
    private lateinit var invalidateCurrentWeatherInformationCache: Job
    private lateinit var removeCacheInformationJob: Job

    override fun validateOpenWeatherApiKeyByPingARequest(resultInvoker: (apiKeyIsValid: Boolean) -> Unit) {
        validateOpenWeatherApiKeyJob = CoroutineScope(Dispatchers.IO).launch {
            startNetworkRequest(
                latitude = 0.0,
                longitude = 0.0,
                responseCallback = { response ->
                    response.ifSuccessful {
                        resultInvoker.invoke(true)
                    }
                    response.ifNotSuccessful { weatherNotFoundError ->
                        if (weatherNotFoundError.httpResponseCode == NetworkInterfaceProvider.NETWORK_AUTHORIZED_FAILED_HTTP_CODE) {
                            resultInvoker.invoke(false)
                        } else {
                            resultInvoker.invoke(true)
                        }
                    }
                },
                responseReceivedCallback = { /* Do nothing */ }
            )
        }
    }

    override fun getCurrentWeatherInformation(
        latitude: Double,
        longitude: Double,
        resultInvoker: (Response<WeatherNotFoundResponse<CurrentWeatherModel>, WeatherNotFoundError>) -> Unit
    ) {
        fetchCurrentWeatherInformationJob = CoroutineScope(Dispatchers.IO).launch {
            if (LocalInterfaceProvider.isCacheMechanismEnabled()) {
                val cacheResponse = currentWeatherDao!!.getCurrentWeatherEntityByCoordinates(
                    latitude = latitude,
                    longitude = longitude
                )

                if (cacheResponse != null) {
                    if (cacheResponse.updatedAt.halfDayPassed()) {
                        // Cache information is no longer valid, should be updated with network request!
                        startNetworkRequest(
                            latitude = latitude,
                            longitude = longitude,
                            responseCallback = resultInvoker,
                            responseReceivedCallback = { responseModel ->
                                cacheResponseModelIntoDatabase(
                                    lastCurrentWeather = cacheResponse,
                                    currentWeatherResponseModel = responseModel
                                )
                            }
                        )
                    } else {
                        val weatherStatusList =
                            currentWeatherDao.getWeatherStatusesByCurrentWeatherId(
                                currentWeatherId = cacheResponse.id!!
                            )
                        resultInvoker.invoke(
                            Success(
                                WeatherNotFoundResponse(
                                    responseType = ResponseType.CACHE,
                                    responseModel = Converters.currentWeatherEntityToCurrentWeatherModel(
                                        currentWeather = cacheResponse,
                                        weatherStatuses = weatherStatusList
                                    )
                                )
                            )
                        )

                    }
                } else {
                    startNetworkRequest(
                        latitude = latitude,
                        longitude = longitude,
                        responseCallback = resultInvoker,
                        responseReceivedCallback = { responseModel ->
                            cacheResponseModelIntoDatabase(
                                lastCurrentWeather = null,
                                currentWeatherResponseModel = responseModel
                            )
                        }
                    )
                }
            } else {
                startNetworkRequest(
                    latitude = latitude,
                    longitude = longitude,
                    responseCallback = resultInvoker,
                    responseReceivedCallback = { /* Do nothing */ }
                )
            }
        }
    }

    override fun removeCacheInformationOlderThan(timeStamp: Long) {
        if (LocalInterfaceProvider.isCacheMechanismDisabled()) return
        removeCacheInformationJob = CoroutineScope(Dispatchers.IO).launch {
            currentWeatherDao!!.deleteCurrentWeatherEntitiesOlderThan(selectedTimeStamp = timeStamp)
        }
    }

    override fun invalidateCache() {
        if (LocalInterfaceProvider.isCacheMechanismDisabled()) return
        invalidateCurrentWeatherInformationCache = CoroutineScope(Dispatchers.IO).launch {
            currentWeatherDao!!.invalidateCache()
        }
    }

    override fun dispose() {
        if (this::validateOpenWeatherApiKeyJob.isInitialized
            && !validateOpenWeatherApiKeyJob.isCompleted
            && !validateOpenWeatherApiKeyJob.isCancelled
        ) {
            validateOpenWeatherApiKeyJob.cancel()
        }

        if (this::fetchCurrentWeatherInformationJob.isInitialized
            && !fetchCurrentWeatherInformationJob.isCompleted
            && !fetchCurrentWeatherInformationJob.isCancelled
        ) {
            fetchCurrentWeatherInformationJob.cancel()
        }

        if (this::invalidateCurrentWeatherInformationCache.isInitialized
            && !invalidateCurrentWeatherInformationCache.isCompleted
            && !invalidateCurrentWeatherInformationCache.isCancelled
        ) {
            removeCacheInformationJob.cancel()
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
        responseCallback: (Response<WeatherNotFoundResponse<CurrentWeatherModel>, WeatherNotFoundError>) -> Unit,
        responseReceivedCallback: suspend (currentWeatherResponseModel: CurrentWeatherResponseModel) -> Unit
    ) {
        val request = RequestProvider.provideCurrentWeatherRequest(
            url = UrlProvider.CURRENT_WEATHER_URL,
            latitude = latitude,
            longitude = longitude
        )
            ?: throw IllegalStateException("Request is null! Check currentWeatherInformation request!")

        try {
            val response = okHttpClient.newCall(request).execute()
            if (response.isSuccessful && response.body != null) {
                val responseModel: CurrentWeatherResponseModel?

                try {
                    responseModel = gsonConverter.fromJson(
                        response.body!!.toString(),
                        CurrentWeatherResponseModel::class.java
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
                            responseModel = Converters.currentWeatherResponseModelToCurrentWeatherModel(
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
        lastCurrentWeather: CurrentWeatherEntity?,
        currentWeatherResponseModel: CurrentWeatherResponseModel,
    ) {
        val finalEntityModel =
            Converters.currentWeatherResponseToCurrentWeatherEntity(
                response = currentWeatherResponseModel,
                entityId = lastCurrentWeather?.id
            )
        if (lastCurrentWeather == null) {
            val currentWeatherId = currentWeatherDao!!.insertCurrentWeatherEntity(finalEntityModel)

            val finalWeatherStatusEntityModels =
                Converters.weatherStatusResponseToWeatherStatusEntity(
                    currentWeatherId = currentWeatherId,
                    weatherStatusResponseModels = currentWeatherResponseModel.weatherStatus
                )

            currentWeatherDao.insertAllWeatherStatusEntities(finalWeatherStatusEntityModels)
        } else {
            // First, delete weather status information that were old
            // Todo: in this case, the right choice is to diff the response with cache information and then update it.
            currentWeatherDao!!.deleteWeatherStatuesByCurrentWeatherId(finalEntityModel.id!!)

            currentWeatherDao.updateCurrentWeatherEntity(finalEntityModel)

            val finalWeatherStatusEntityModels =
                Converters.weatherStatusResponseToWeatherStatusEntity(
                    currentWeatherId = finalEntityModel.id,
                    weatherStatusResponseModels = currentWeatherResponseModel.weatherStatus
                )

            currentWeatherDao.insertAllWeatherStatusEntities(finalWeatherStatusEntityModels)
        }
    }
}
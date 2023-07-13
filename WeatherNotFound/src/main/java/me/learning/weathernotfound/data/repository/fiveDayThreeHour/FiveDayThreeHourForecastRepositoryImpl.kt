package me.learning.weathernotfound.data.repository.fiveDayThreeHour

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import me.learning.weathernotfound.data.local.LocalInterfaceProvider
import me.learning.weathernotfound.data.local.dao.FiveDayThreeHourDao
import me.learning.weathernotfound.data.network.providers.RequestProvider
import me.learning.weathernotfound.data.network.providers.UrlProvider
import me.learning.weathernotfound.data.repository.Failure
import me.learning.weathernotfound.data.repository.Response
import me.learning.weathernotfound.data.repository.Success
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.Converters
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.databaseModels.FiveDayThreeHourForecastEntity
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.databaseModels.WeatherInformationEntity
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.networkModels.FiveDayThreeHourForecastResponseModel
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.presentationModels.FiveDayThreeHourForecastModel
import me.learning.weathernotfound.presentation.ResponseType
import me.learning.weathernotfound.presentation.WeatherNotFoundError
import me.learning.weathernotfound.presentation.WeatherNotFoundResponse
import me.learning.weathernotfound.utils.Utilities.halfDayPassed
import okhttp3.OkHttpClient

internal class FiveDayThreeHourForecastRepositoryImpl(
    private val fiveDayThreeHourDao: FiveDayThreeHourDao?,
    private val okHttpClient: OkHttpClient,
    private val gsonConverter: Gson
) : FiveDayThreeHourForecastRepository {

    private lateinit var fetchFiveDayThreeHourForecastInformationJob: Job
    private lateinit var invalidateFiveDayThreeHourForecastCacheJob: Job
    private lateinit var removeCacheInformationJob: Job

    override fun getFiveDayThreeHourForecastInformation(
        latitude: Double,
        longitude: Double,
        resultInvoker: suspend (Response<WeatherNotFoundResponse<FiveDayThreeHourForecastModel>, WeatherNotFoundError>) -> Unit,
    ) {
        fetchFiveDayThreeHourForecastInformationJob = CoroutineScope(Dispatchers.IO).launch {
            if (LocalInterfaceProvider.isCacheMechanismEnabled()) {
                val fiveDayCacheResponse =
                    fiveDayThreeHourDao!!.getFiveThreeHourForecastEntityByCoordinates(
                        latitude = latitude,
                        longitude = longitude
                    )
                if (fiveDayCacheResponse != null) {
                    val forecastCacheResponse =
                        fiveDayThreeHourDao.getForecastEntitiesListByFiveDayForecastId(
                            fiveDayForecastId = fiveDayCacheResponse.id!!
                        )
                    if (!forecastCacheResponse.isNullOrEmpty()) {
                        if (fiveDayCacheResponse.updatedAt.halfDayPassed()) {
                            // Cache information is no longer valid, should be updated with network request!
                            startNetworkRequest(
                                latitude = latitude,
                                longitude = longitude,
                                responseCallback = resultInvoker,
                                responseReceivedCallback = { responseModel ->
                                    cacheResponseModelIntoDatabase(
                                        lastFiveDayThreeHourForecastEntity = fiveDayCacheResponse,
                                        fiveDayThreeHourForecastResponseModel = responseModel
                                    )
                                }
                            )
                        } else {
                            /*
                            We have several ideas about select query like this, but because we don't
                            have much records in this scenario, it's ok to query for each forecast
                            weather information!
                            */
                            val weatherInformationCacheResponse =
                                mutableListOf<List<WeatherInformationEntity>>()
                            forecastCacheResponse.forEach { forecastEntity ->
                                weatherInformationCacheResponse.add(
                                    fiveDayThreeHourDao.getWeatherInformationEntitiesByForecastId(
                                        forecastId = forecastEntity.id!!
                                    )
                                )
                            }

                            resultInvoker.invoke(
                                Success(
                                    WeatherNotFoundResponse(
                                        responseType = ResponseType.CACHE,
                                        responseModel = Converters.fiveDayThreeOurForecastEntityToFiveDayThreeHourForecastModel(
                                            fiveDayEntity = fiveDayCacheResponse,
                                            forecastEntities = forecastCacheResponse,
                                            weatherInformationEntities = weatherInformationCacheResponse
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
                                    lastFiveDayThreeHourForecastEntity = fiveDayCacheResponse,
                                    fiveDayThreeHourForecastResponseModel = responseModel
                                )
                            }
                        )
                    }
                } else {
                    startNetworkRequest(
                        latitude = latitude,
                        longitude = longitude,
                        responseCallback = resultInvoker,
                        responseReceivedCallback = { responseModel ->
                            cacheResponseModelIntoDatabase(
                                lastFiveDayThreeHourForecastEntity = null,
                                fiveDayThreeHourForecastResponseModel = responseModel
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
            fiveDayThreeHourDao!!.deleteFiveDayThreeHourEntitiesOlderThan(timeStamp)
        }
    }

    override fun invalidateCache() {
        if (LocalInterfaceProvider.isCacheMechanismDisabled()) return
        invalidateFiveDayThreeHourForecastCacheJob = CoroutineScope(Dispatchers.IO).launch {
            fiveDayThreeHourDao!!.invalidateCache()
        }
    }

    override fun dispose() {
        if (this::fetchFiveDayThreeHourForecastInformationJob.isInitialized
            && !fetchFiveDayThreeHourForecastInformationJob.isCompleted
            && !fetchFiveDayThreeHourForecastInformationJob.isCancelled
        ) {
            fetchFiveDayThreeHourForecastInformationJob.cancel()
        }

        if (this::invalidateFiveDayThreeHourForecastCacheJob.isInitialized
            && !invalidateFiveDayThreeHourForecastCacheJob.isCompleted
            && !invalidateFiveDayThreeHourForecastCacheJob.isCancelled
        ) {
            invalidateFiveDayThreeHourForecastCacheJob.cancel()
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
        responseCallback: suspend (Response<WeatherNotFoundResponse<FiveDayThreeHourForecastModel>, WeatherNotFoundError>) -> Unit,
        responseReceivedCallback: suspend (fiveDayThreeHourForecastResponseModel: FiveDayThreeHourForecastResponseModel) -> Unit
    ) {
        val request = RequestProvider.provideFiveDayThreeHourForecastRequest(
            url = UrlProvider.FIVE_DAY_THREE_HOUR_FORECAST_URL,
            latitude = latitude,
            longitude = longitude
        ) ?: throw IllegalStateException("Request is null! Check fiveDayThreeHour request!")

        try {
            val response = okHttpClient.newCall(request).execute()
            if (response.isSuccessful && response.body != null) {
                val responseModel: FiveDayThreeHourForecastResponseModel

                try {
                    responseModel = gsonConverter.fromJson(
                        response.body!!.string(),
                        FiveDayThreeHourForecastResponseModel::class.java
                    )
                } catch (jsonSyntaxException: JsonSyntaxException) {
                    responseCallback.invoke(
                        Failure(
                            WeatherNotFoundError(
                                exception = jsonSyntaxException,
                                internalErrorMessage = "Failed to parse response into FiveDayThreeHourForecastModel!"
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
                            responseModel = Converters.fiveDayThreeHourForecastResponseToFiveDayThreeHourForecastModel(
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
        lastFiveDayThreeHourForecastEntity: FiveDayThreeHourForecastEntity?,
        fiveDayThreeHourForecastResponseModel: FiveDayThreeHourForecastResponseModel,
    ) {

        if (lastFiveDayThreeHourForecastEntity == null) {
            insertCacheIntoDatabase(
                fiveDayThreeHourForecastResponseModel = fiveDayThreeHourForecastResponseModel
            )
        } else {
            updateCacheOfDatabase(
                lastFiveDayThreeHourForecastEntity = lastFiveDayThreeHourForecastEntity,
                fiveDayThreeHourForecastResponseModel = fiveDayThreeHourForecastResponseModel
            )
        }
    }

    private suspend fun insertCacheIntoDatabase(
        fiveDayThreeHourForecastResponseModel: FiveDayThreeHourForecastResponseModel
    ) {
        val finalFiveDayThreeHourForecastEntityModel =
            Converters.fiveDayThreeHourForecastResponseToFiveDayThreeHourForecastEntity(
                response = fiveDayThreeHourForecastResponseModel,
                entityId = null
            )

        // Insert parent information
        val forecastId = fiveDayThreeHourDao!!.insertFiveDayThreeHourForecastEntity(
            fiveDayThreeHourForecastEntity = finalFiveDayThreeHourForecastEntityModel
        )

        val finalForecastEntityModel =
            Converters.forecastResponseToForecastEntity(
                responses = fiveDayThreeHourForecastResponseModel.forecastResponseModels,
                forecastId = forecastId
            )

        // Insert every forecast information
        fiveDayThreeHourDao.insertListOfForecastEntities(finalForecastEntityModel)
            .forEachIndexed { index, id ->
                val finalWeatherInformationEntityModel =
                    Converters.weatherInformationResponseToWeatherInformationEntity(
                        responses = fiveDayThreeHourForecastResponseModel.forecastResponseModels[index].weatherInformation,
                        forecastId = id
                    )
                // And finally Insert every weather information of forecast information
                fiveDayThreeHourDao.insertListOfWeatherInformationEntities(
                    weatherInformationEntities = finalWeatherInformationEntityModel
                )
            }
    }

    private suspend fun updateCacheOfDatabase(
        lastFiveDayThreeHourForecastEntity: FiveDayThreeHourForecastEntity,
        fiveDayThreeHourForecastResponseModel: FiveDayThreeHourForecastResponseModel,
    ) {
        val finalFiveDayThreeHourForecastEntityModel =
            Converters.fiveDayThreeHourForecastResponseToFiveDayThreeHourForecastEntity(
                response = fiveDayThreeHourForecastResponseModel,
                entityId = lastFiveDayThreeHourForecastEntity.id
            )

        // First, delete forecast and weather information that were old
        // Todo: in this case, the right choice is to diff the response with cache information and then update it.
        fiveDayThreeHourDao!!.deleteForecastEntitiesByFiveDayForecastId(
            fiveDayForecastId = lastFiveDayThreeHourForecastEntity.id!!
        )

        fiveDayThreeHourDao.updateFiveDayThreeHourForecastEntity(
            fiveDayThreeHourForecastEntity = finalFiveDayThreeHourForecastEntityModel
        )

        val finalForecastEntityModel =
            Converters.forecastResponseToForecastEntity(
                responses = fiveDayThreeHourForecastResponseModel.forecastResponseModels,
                forecastId = lastFiveDayThreeHourForecastEntity.id
            )

        // Insert every forecast information
        fiveDayThreeHourDao.insertListOfForecastEntities(finalForecastEntityModel)
            .forEachIndexed { index, id ->
                val finalWeatherInformationEntityModel =
                    Converters.weatherInformationResponseToWeatherInformationEntity(
                        responses = fiveDayThreeHourForecastResponseModel.forecastResponseModels[index].weatherInformation,
                        forecastId = id
                    )
                // And finally Insert every weather information of forecast information
                fiveDayThreeHourDao.insertListOfWeatherInformationEntities(
                    weatherInformationEntities = finalWeatherInformationEntityModel
                )
            }
    }
}
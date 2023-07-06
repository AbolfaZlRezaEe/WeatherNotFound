package me.learning.weathernotfound.presentation

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.learning.weathernotfound.data.local.LocalInterfaceProvider
import me.learning.weathernotfound.data.network.providers.NetworkInterfaceProvider
import me.learning.weathernotfound.data.repository.ifNotSuccessful
import me.learning.weathernotfound.data.repository.ifSuccessful
import me.learning.weathernotfound.data.repository.provider.RepositoryProvider
import me.learning.weathernotfound.domain.currentWeather.presentationModels.CurrentWeatherModel
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.presentationModels.FiveDayThreeHourForecastModel
import okhttp3.logging.HttpLoggingInterceptor

class WeatherNotFound private constructor() {

    private var openWeatherApiKeyIsRegistered = true

    companion object {
        private const val TAG = "WeatherNotFound"

        private var INSTANCE: WeatherNotFound? = null

        @Synchronized
        fun getInstance(): WeatherNotFound {
            if (INSTANCE == null) {
                INSTANCE = WeatherNotFound()
            }
            return INSTANCE!!
        }
    }

    fun init(
        context: Context,
        httpLoggingLevel: HttpLoggingInterceptor.Level? = null,
        readTimeoutInSeconds: Long? = null,
        connectTimeoutInSeconds: Long? = null,
        cacheMechanismEnabled: Boolean = false,
    ) {
        LocalInterfaceProvider.setCacheMechanism(cacheMechanismEnabled)
        if (cacheMechanismEnabled) {
            LocalInterfaceProvider.init(context)
        }

        NetworkInterfaceProvider.initGsonConverter()

        if (httpLoggingLevel != null
            || readTimeoutInSeconds != null
            || connectTimeoutInSeconds != null
        ) {
            NetworkInterfaceProvider.initManually(
                httpLoggingLevel = httpLoggingLevel,
                readTimeoutInSeconds = readTimeoutInSeconds,
                connectTimeoutInSeconds = connectTimeoutInSeconds
            )
        } else {
            NetworkInterfaceProvider.init()
        }

        validateOpenWeatherApiKey {}
    }

    fun getCurrentWeatherInformation(
        latitude: Double,
        longitude: Double,
        weatherNotFoundCallback: WeatherNotFoundCallback<WeatherNotFoundResponse<CurrentWeatherModel>, WeatherNotFoundError>,
    ) {
        if (!openWeatherApiKeyIsRegistered) {
            validateOpenWeatherApiKey { registered ->
                if (registered) {
                    getCurrentWeatherInformation(
                        latitude = latitude,
                        longitude = longitude,
                        weatherNotFoundCallback = weatherNotFoundCallback
                    )
                }
            }
            return
        }
        RepositoryProvider.getCurrentWeatherRepository().getCurrentWeatherInformation(
            latitude = latitude,
            longitude = longitude
        ) { response ->
            CoroutineScope(Dispatchers.Main).launch {
                response.ifSuccessful { successfulResponse ->
                    weatherNotFoundCallback.onSuccess(
                        response = successfulResponse
                    )
                }
                response.ifNotSuccessful { errorResponse ->
                    weatherNotFoundCallback.onError(
                        error = errorResponse
                    )
                }
            }
        }
    }

    fun getFiveDayThreeHourForecastInformation(
        latitude: Double,
        longitude: Double,
        weatherNotFoundCallback: WeatherNotFoundCallback<WeatherNotFoundResponse<FiveDayThreeHourForecastModel>, WeatherNotFoundError>,
    ) {
        if (!openWeatherApiKeyIsRegistered) {
            validateOpenWeatherApiKey { registered ->
                if (registered) {
                    getFiveDayThreeHourForecastInformation(
                        latitude = latitude,
                        longitude = longitude,
                        weatherNotFoundCallback = weatherNotFoundCallback
                    )
                }
            }
            return
        }
        RepositoryProvider.getFiveDayThreeHourForecastRepository()
            .getFiveDayThreeHourForecastInformation(
                latitude = latitude,
                longitude = longitude,
            ) { response ->
                CoroutineScope(Dispatchers.Main).launch {
                    response.ifSuccessful { successfulResponse ->
                        weatherNotFoundCallback.onSuccess(
                            response = successfulResponse
                        )
                    }
                    response.ifNotSuccessful { errorResponse ->
                        weatherNotFoundCallback.onError(
                            error = errorResponse
                        )
                    }
                }
            }
    }

    private fun validateOpenWeatherApiKey(validationResult: (isRegistered: Boolean) -> Unit) {
        RepositoryProvider.getCurrentWeatherRepository()
            .validateOpenWeatherApiKeyByPingARequest { result ->
                openWeatherApiKeyIsRegistered = result
                validationResult.invoke(result)
                if (!result) {
                    Log.e(
                        TAG, "Validation failed! Your Api key is not working... Please ensure" +
                                " about your payment in OpenWeatherApi!"
                    )
                }
            }
    }
}
package me.learning.weathernotfound.presentation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
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
        private var initCalled = false

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
        checkInternetPermission(context)

        setInitCalledState(true)

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
        cityName: String,
        limit: Int,
        weatherNotFoundCallback: WeatherNotFoundCallback<WeatherNotFoundResponse<CurrentWeatherModel>, WeatherNotFoundError>,
    ) {
        validateInitFunction()
        if (!openWeatherApiKeyIsRegistered) {
            validateOpenWeatherApiKey { registered ->
                if (registered) {
                    getCurrentWeatherInformation(
                        cityName = cityName,
                        limit = limit,
                        weatherNotFoundCallback = weatherNotFoundCallback
                    )
                }
            }
            return
        }
        RepositoryProvider.getDirectRepository()
            .getCityNameCoordinatesInformation(
                cityName = cityName,
                limit = limit,
            ) { response ->
                response.ifSuccessful { result ->
                    getCurrentWeatherInformation(
                        latitude = result.responseModel[0].locationCoordinates.latitude,
                        longitude = result.responseModel[0].locationCoordinates.longitude,
                        weatherNotFoundCallback = weatherNotFoundCallback
                    )
                }
                response.ifNotSuccessful { error ->
                    weatherNotFoundCallback.onError(error)
                }
            }
    }

    fun getCurrentWeatherInformation(
        latitude: Double,
        longitude: Double,
        weatherNotFoundCallback: WeatherNotFoundCallback<WeatherNotFoundResponse<CurrentWeatherModel>, WeatherNotFoundError>,
    ) {
        validateInitFunction()
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
        cityName: String,
        limit: Int,
        weatherNotFoundCallback: WeatherNotFoundCallback<WeatherNotFoundResponse<FiveDayThreeHourForecastModel>, WeatherNotFoundError>,
    ) {
        validateInitFunction()
        if (!openWeatherApiKeyIsRegistered) {
            validateOpenWeatherApiKey { registered ->
                if (registered) {
                    getFiveDayThreeHourForecastInformation(
                        cityName = cityName,
                        limit = limit,
                        weatherNotFoundCallback = weatherNotFoundCallback
                    )
                }
            }
            return
        }
        RepositoryProvider.getDirectRepository()
            .getCityNameCoordinatesInformation(
                cityName = cityName,
                limit = limit,
            ) { response ->
                response.ifSuccessful { result ->
                    getFiveDayThreeHourForecastInformation(
                        latitude = result.responseModel[0].locationCoordinates.latitude,
                        longitude = result.responseModel[0].locationCoordinates.longitude,
                        weatherNotFoundCallback = weatherNotFoundCallback
                    )
                }
                response.ifNotSuccessful { error ->
                    weatherNotFoundCallback.onError(error)
                }
            }
    }

    fun getFiveDayThreeHourForecastInformation(
        latitude: Double,
        longitude: Double,
        weatherNotFoundCallback: WeatherNotFoundCallback<WeatherNotFoundResponse<FiveDayThreeHourForecastModel>, WeatherNotFoundError>,
    ) {
        validateInitFunction()
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
                                " about your ApiKey in OpenWeatherMap user panel!"
                    )
                }
            }
    }

    @Synchronized
    private fun setInitCalledState(isCalled: Boolean) {
        initCalled = isCalled
    }

    private fun isInitCalled(): Boolean {
        return initCalled
    }

    private fun validateInitFunction() {
        if (!isInitCalled()) {
            throw IllegalStateException(
                "You didn't call init() function of WeatherNotFound! " +
                        "Make sure you call this function on your Application class!"
            )
        }
    }

    private fun checkInternetPermission(context: Context) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.INTERNET
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            throw IllegalStateException(
                "Please ensure you have INTERNET permission in your application!" +
                        " You can't use WeatherNotFound unless you add this permission to your Manifest file!"
            )
        }
    }
}
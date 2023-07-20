package me.learning.weathernotfound.presentation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.learning.weathernotfound.data.local.LocalInterfaceProvider
import me.learning.weathernotfound.data.network.providers.NetworkInterfaceProvider
import me.learning.weathernotfound.data.repository.ifNotSuccessful
import me.learning.weathernotfound.data.repository.ifSuccessful
import me.learning.weathernotfound.data.repository.provider.RepositoryProvider
import me.learning.weathernotfound.domain.currentWeather.presentationModels.CurrentWeatherModel
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.presentationModels.FiveDayThreeHourForecastModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class WeatherNotFound private constructor() {

    private var openWeatherApiKeyIsRegistered = true

    companion object {
        private const val TAG = "WeatherNotFound"

        private var INSTANCE: WeatherNotFound? = null
        private var initCalled = false

        /**
         * @return the singleton instance of WeatherNotFound
         */
        @Synchronized
        fun getInstance(): WeatherNotFound {
            if (INSTANCE == null) {
                INSTANCE = WeatherNotFound()
            }
            return INSTANCE!!
        }
    }

    /**
     * Initialize the network and local interface of WeatherNotFound library.
     *
     * @param context use for checking Internet permission and creating local interface.
     * @param httpLoggingLevel use for setting into the WeatherNotFound [OkHttpClient]. if you don't
     * specify this variable, it will be [HttpLoggingInterceptor.Level.NONE].
     * @param readTimeoutInSeconds use for setting into the WeatherNotFound [OkHttpClient]. if you don't
     * specify this variable, it will be **5 Seconds**.
     * @param connectTimeoutInSeconds use for setting into the WeatherNotFound [OkHttpClient]. if you don't
     * specify this variable, it will be **5 Seconds**.
     * @param cacheMechanismEnabled if this variable set to **true**, database will be created, otherwise
     * caching system will be disabled!
     */
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

    /**
     * By calling this, every request and operation in WeatherNotFound will be stopped!
     */
    fun destroy() {
        RepositoryProvider.getActualFiveDayThreeHourRepository()?.dispose()
        RepositoryProvider.getActualDirectRepository()?.dispose()
        RepositoryProvider.getActualCurrentWeatherRepository()?.dispose()
    }

    /**
     * Fetch Current Weather information for given [cityName].
     *
     * NOTE: The request will be canceled if your ApiKey is not valid!
     *
     * @param cityName
     * @param weatherNotFoundCallback the result of the request
     */
    fun getCurrentWeatherInformation(
        cityName: String,
        weatherNotFoundCallback: WeatherNotFoundCallback<WeatherNotFoundResponse<CurrentWeatherModel>, WeatherNotFoundError>,
    ) {
        validateInitFunction()
        if (!openWeatherApiKeyIsRegistered) {
            validateOpenWeatherApiKey { registered ->
                if (registered) {
                    getCurrentWeatherInformation(
                        cityName = cityName,
                        weatherNotFoundCallback = weatherNotFoundCallback
                    )
                }
            }
            return
        }
        RepositoryProvider.getDirectRepository()
            .getCityNameCoordinatesInformation(
                cityName = cityName,
                limit = NetworkInterfaceProvider.GEOCODING_LIMIT_REQUEST_PARAMETER_VALUE,
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

    /**
     * Fetch Current Weather information for given [latitude] and [longitude].
     *
     * NOTE: The request will be canceled if your ApiKey is not valid!
     *
     * @param latitude
     * @param longitude
     * @param weatherNotFoundCallback the result of the request
     */
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
            withContext(Dispatchers.Main) {
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

    /**
     * Fetch 5 day 3 hour information for given [cityName].
     *
     * NOTE: The request will be canceled if your ApiKey is not valid!
     *
     * @param cityName
     * @param weatherNotFoundCallback the result of the request
     */
    fun getFiveDayThreeHourForecastInformation(
        cityName: String,
        weatherNotFoundCallback: WeatherNotFoundCallback<WeatherNotFoundResponse<FiveDayThreeHourForecastModel>, WeatherNotFoundError>,
    ) {
        validateInitFunction()
        if (!openWeatherApiKeyIsRegistered) {
            validateOpenWeatherApiKey { registered ->
                if (registered) {
                    getFiveDayThreeHourForecastInformation(
                        cityName = cityName,
                        weatherNotFoundCallback = weatherNotFoundCallback
                    )
                }
            }
            return
        }
        RepositoryProvider.getDirectRepository()
            .getCityNameCoordinatesInformation(
                cityName = cityName,
                limit = NetworkInterfaceProvider.GEOCODING_LIMIT_REQUEST_PARAMETER_VALUE,
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

    /**
     * Fetch 5 day 3 hour information for given [latitude] and [longitude].
     *
     * NOTE: The request will be canceled if your ApiKey is not valid!
     *
     * @param latitude
     * @param longitude
     * @param weatherNotFoundCallback the result of the request
     */
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
                withContext(Dispatchers.Main) {
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

    /**
     * Invalidate and clear all cached information of Current Weather requests. also, it will
     * invalidate and clear all direct geocoding information as well.
     */
    fun invalidateCurrentWeatherCache() {
        RepositoryProvider.getCurrentWeatherRepository().invalidateCache()
        RepositoryProvider.getDirectRepository().invalidateCache()
    }

    /**
     * Invalidate and clear all cached information of 5 day 3 hour requests. also, it will
     * invalidate and clear all direct geocoding information as well.
     */
    fun invalidateFiveDayThreeHourForecastCache() {
        RepositoryProvider.getFiveDayThreeHourForecastRepository().invalidateCache()
        RepositoryProvider.getDirectRepository().invalidateCache()
    }

    /**
     * Validate your Apikey using a sample request to OpenWeatherMap APIs.
     *
     * @param validationResult emit the result with true or false. true if api key is valid or request
     * failed for any reason, false if HttpCode of request was 401.
     */
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
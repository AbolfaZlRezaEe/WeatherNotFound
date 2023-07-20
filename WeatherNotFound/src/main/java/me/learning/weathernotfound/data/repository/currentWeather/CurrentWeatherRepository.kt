package me.learning.weathernotfound.data.repository.currentWeather

import me.learning.weathernotfound.data.repository.Response
import me.learning.weathernotfound.domain.currentWeather.presentationModels.CurrentWeatherModel
import me.learning.weathernotfound.presentation.WeatherNotFoundError
import me.learning.weathernotfound.presentation.WeatherNotFoundResponse

internal interface CurrentWeatherRepository {

    /**
     * Send a currentWeather sample request with latitude and longitude of 0.0 to test the api key.
     *
     * @param resultInvoker emit the result with true or false. true if api key is valid or request
     * failed for any reason, false if HttpCode of request was 401.
     */
    fun validateOpenWeatherApiKeyByPingARequest(resultInvoker: (apiKeyIsValid: Boolean) -> Unit)

    /**
     * Fetch Current weather information of given [latitude] and [longitude]. after receiving the result,
     * if the result was successful and cache mechanism was enabled, response will be cached for half day.
     *
     * NOTE: cache will not delete after half day, it will request for new data again and update it into the database
     * next time you call this function.
     *
     * @param resultInvoker emit the result of request with the same thread that request executed!
     */
    fun getCurrentWeatherInformation(
        latitude: Double,
        longitude: Double,
        resultInvoker: suspend (Response<WeatherNotFoundResponse<CurrentWeatherModel>, WeatherNotFoundError>) -> Unit
    )

    /**
     * Remove all the information which are older than selected [timeStamp]
     */
    fun removeCacheInformationOlderThan(timeStamp: Long)

    /**
     * Clear the cache data of all tables of CurrentWeather information.
     */
    fun invalidateCache()

    /**
     * Dispose and cancel all requests and operations that started in repository.
     */
    fun dispose()
}
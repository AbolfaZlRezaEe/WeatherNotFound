package me.learning.weathernotfound.data.repository.currentWeather

import me.learning.weathernotfound.data.repository.Response
import me.learning.weathernotfound.domain.currentWeather.presentationModels.CurrentWeatherModel
import me.learning.weathernotfound.presentation.WeatherNotFoundError
import me.learning.weathernotfound.presentation.WeatherNotFoundResponse

internal interface CurrentWeatherRepository {

    fun validateOpenWeatherApiKeyByPingARequest(resultInvoker: (apiKeyIsValid: Boolean) -> Unit)

    fun getCurrentWeatherInformation(
        latitude: Double,
        longitude: Double,
        resultInvoker: (Response<WeatherNotFoundResponse<CurrentWeatherModel>, WeatherNotFoundError>) -> Unit
    )

    fun removeCacheInformationOlderThan(timeStamp: Long)

    fun invalidateCache()

    fun dispose()
}
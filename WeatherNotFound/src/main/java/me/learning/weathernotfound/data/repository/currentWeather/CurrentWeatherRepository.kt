package me.learning.weathernotfound.data.repository.currentWeather

import me.learning.weathernotfound.data.repository.Response
import me.learning.weathernotfound.data.repository.WeatherNotFoundError
import me.learning.weathernotfound.data.repository.WeatherNotFoundResponse
import me.learning.weathernotfound.domain.currentWeather.presentationModels.CurrentWeatherModel

internal interface CurrentWeatherRepository {

    fun getCurrentWeatherInformation(
        latitude: Double,
        longitude: Double,
        resultInvoker: (Response<WeatherNotFoundResponse<CurrentWeatherModel>, WeatherNotFoundError>) -> Unit
    )

    fun removeCacheInformationOlderThan(timeStamp: Long)

    fun invalidateCache()

    fun dispose()
}
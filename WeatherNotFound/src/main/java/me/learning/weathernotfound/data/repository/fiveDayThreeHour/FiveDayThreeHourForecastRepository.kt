package me.learning.weathernotfound.data.repository.fiveDayThreeHour

import me.learning.weathernotfound.data.repository.Response
import me.learning.weathernotfound.presentation.WeatherNotFoundError
import me.learning.weathernotfound.presentation.WeatherNotFoundResponse
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.presentationModels.FiveDayThreeHourForecastModel

internal interface FiveDayThreeHourForecastRepository {

    fun getFiveDayThreeHourForecastInformation(
        latitude: Double,
        longitude: Double,
        resultInvoker: suspend (Response<WeatherNotFoundResponse<FiveDayThreeHourForecastModel>, WeatherNotFoundError>) -> Unit,
    )

    fun removeCacheInformationOlderThan(timeStamp: Long)

    fun invalidateCache()

    fun dispose()
}
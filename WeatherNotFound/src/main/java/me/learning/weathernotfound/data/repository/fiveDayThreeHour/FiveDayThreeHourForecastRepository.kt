package me.learning.weathernotfound.data.repository.fiveDayThreeHour

import me.learning.weathernotfound.data.repository.Response
import me.learning.weathernotfound.data.repository.WeatherNotFoundError
import me.learning.weathernotfound.data.repository.WeatherNotFoundResponse
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.presentationModels.FiveDayThreeHourForecastModel

internal interface FiveDayThreeHourForecastRepository {

    fun getFiveDayThreeHourForecastInformation(
        latitude: Double,
        longitude: Double,
        resultInvoker: (Response<WeatherNotFoundResponse<FiveDayThreeHourForecastModel>, WeatherNotFoundError>) -> Unit,
    )

    fun removeCacheInformationOlderThan(timeStamp: Long)

    fun invalidateCache()

    fun dispose()
}
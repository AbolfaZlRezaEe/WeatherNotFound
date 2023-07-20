package me.learning.weathernotfound.data.repository.fiveDayThreeHour

import me.learning.weathernotfound.data.repository.Response
import me.learning.weathernotfound.presentation.WeatherNotFoundError
import me.learning.weathernotfound.presentation.WeatherNotFoundResponse
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.presentationModels.FiveDayThreeHourForecastModel

internal interface FiveDayThreeHourForecastRepository {

    /**
     * Fetch 5 day 3 hour information of given coordinates. after receiving the result,
     * if the result was successful and cache mechanism was enabled, response will be cached for half day.
     *
     * NOTE: cache will not delete after half day, it will request for new data again and update it into the database
     * next time you call this function.
     *
     * @param latitude
     * @param longitude
     * @param resultInvoker emit the result of request with the same thread that request executed!
     */
    fun getFiveDayThreeHourForecastInformation(
        latitude: Double,
        longitude: Double,
        resultInvoker: suspend (Response<WeatherNotFoundResponse<FiveDayThreeHourForecastModel>, WeatherNotFoundError>) -> Unit,
    )

    /**
     * Remove all the information which are older than selected [timeStamp]
     */
    fun removeCacheInformationOlderThan(timeStamp: Long)

    /**
     * Clear the cache data of all tables of FiveDayThreeHourForecast information.
     */
    fun invalidateCache()

    /**
     * Dispose and cancel all requests and operations that started in repository.
     */
    fun dispose()
}
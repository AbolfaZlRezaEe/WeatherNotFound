package me.learning.weathernotfound.data.repository.directGeocoding

import me.learning.weathernotfound.data.repository.Response
import me.learning.weathernotfound.domain.directGeocoding.presentationModels.DirectGeocodingModel
import me.learning.weathernotfound.presentation.WeatherNotFoundError
import me.learning.weathernotfound.presentation.WeatherNotFoundResponse

internal interface DirectGeocodingRepository {

    /**
     * Fetch coordinates information of given [cityName]. after receiving the result,
     * if the result was successful and cache mechanism was enabled, response will be cached for three days.
     *
     * NOTE: cache will not delete after three days, it will request for new data again and update it into the database
     * next time you call this function.
     *
     * @param cityName
     * @param limit
     * @param resultInvoker emit the result of request with the same thread that request executed!
     */
    fun getCityNameCoordinatesInformation(
        cityName: String,
        limit: Int,
        resultInvoker: suspend (Response<WeatherNotFoundResponse<DirectGeocodingModel>, WeatherNotFoundError>) -> Unit
    )

    /**
     * Remove all the information which are older than selected [timeStamp]
     */
    fun removeCacheInformationOlderThen(timeStamp: Long)

    /**
     * Clear the cache data of all tables of DirectGeocoding information.
     */
    fun invalidateCache()

    /**
     * Dispose and cancel all requests and operations that started in repository.
     */
    fun dispose()
}
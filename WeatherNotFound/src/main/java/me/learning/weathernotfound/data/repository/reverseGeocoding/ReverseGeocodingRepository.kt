package me.learning.weathernotfound.data.repository.reverseGeocoding

import me.learning.weathernotfound.data.repository.Response
import me.learning.weathernotfound.presentation.WeatherNotFoundError
import me.learning.weathernotfound.presentation.WeatherNotFoundResponse
import me.learning.weathernotfound.domain.reverseGeocoding.presentationModels.ReverseGeocodingModel

internal interface ReverseGeocodingRepository {

    /**
     * Fetch coordinates information of given coordinates. after receiving the result,
     * if the result was successful and cache mechanism was enabled, response will be cached for three days.
     *
     * NOTE: cache will not delete after three days, it will request for new data again and update it into the database
     * next time you call this function.
     *
     * @param latitude
     * @param longitude
     * @param limit
     * @param resultInvoker emit the result of request with the same thread that request executed!
     */
    fun getCoordinateInformation(
        latitude: Double,
        longitude: Double,
        limit: Int,
        resultInvoker: suspend (Response<WeatherNotFoundResponse<ReverseGeocodingModel>, WeatherNotFoundError>) -> Unit
    )

    /**
     * Remove all the information which are older than selected [timeStamp]
     */
    fun removeCacheInformationOlderThan(timeStamp: Long)

    /**
     * Clear the cache data of all tables of ReverseGeocoding information.
     */
    fun invalidateCache()

    /**
     * Dispose and cancel all requests and operations that started in repository.
     */
    fun dispose()
}
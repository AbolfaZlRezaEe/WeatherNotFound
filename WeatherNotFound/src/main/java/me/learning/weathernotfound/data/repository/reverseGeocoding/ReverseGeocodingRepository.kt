package me.learning.weathernotfound.data.repository.reverseGeocoding

import me.learning.weathernotfound.data.repository.Response
import me.learning.weathernotfound.data.repository.WeatherNotFoundError
import me.learning.weathernotfound.data.repository.WeatherNotFoundResponse
import me.learning.weathernotfound.domain.reverseGeocoding.presentationModels.LocationInfoModel

internal interface ReverseGeocodingRepository {

    fun getCoordinateInformation(
        latitude: Double,
        longitude: Double,
        limit: Int,
        resultInvoker: (Response<WeatherNotFoundResponse<LocationInfoModel>, WeatherNotFoundError>) -> Unit
    )

    fun removeCacheInformationOlderThan(timeStamp: Long)

    fun invalidateCache()

    fun dispose()
}
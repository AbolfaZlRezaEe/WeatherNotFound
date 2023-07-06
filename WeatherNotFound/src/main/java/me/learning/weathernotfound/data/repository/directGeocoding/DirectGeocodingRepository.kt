package me.learning.weathernotfound.data.repository.directGeocoding

import me.learning.weathernotfound.data.repository.Response
import me.learning.weathernotfound.data.repository.WeatherNotFoundError
import me.learning.weathernotfound.data.repository.WeatherNotFoundResponse
import me.learning.weathernotfound.domain.directGeocoding.presentationModels.DirectGeocodingModel

internal interface DirectGeocodingRepository {

    fun getCityNameCoordinatesInformation(
        cityName: String,
        limit: Int,
        resultInvoker: (Response<WeatherNotFoundResponse<DirectGeocodingModel>, WeatherNotFoundError>) -> Unit
    )

    fun removeCacheInformationOlderThen(timeStamp: Long)

    fun invalidateCache()

    fun dispose()
}
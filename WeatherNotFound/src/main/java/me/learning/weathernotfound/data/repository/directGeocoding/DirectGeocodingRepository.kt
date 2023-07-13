package me.learning.weathernotfound.data.repository.directGeocoding

import me.learning.weathernotfound.data.repository.Response
import me.learning.weathernotfound.domain.directGeocoding.presentationModels.DirectGeocodingModel
import me.learning.weathernotfound.presentation.WeatherNotFoundError
import me.learning.weathernotfound.presentation.WeatherNotFoundResponse

internal interface DirectGeocodingRepository {

    fun getCityNameCoordinatesInformation(
        cityName: String,
        limit: Int,
        resultInvoker: suspend (Response<WeatherNotFoundResponse<DirectGeocodingModel>, WeatherNotFoundError>) -> Unit
    )

    fun removeCacheInformationOlderThen(timeStamp: Long)

    fun invalidateCache()

    fun dispose()
}
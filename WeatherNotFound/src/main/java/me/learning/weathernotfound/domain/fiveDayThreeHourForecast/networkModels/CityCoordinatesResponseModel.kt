package me.learning.weathernotfound.domain.fiveDayThreeHourForecast.networkModels

import com.google.gson.annotations.SerializedName

/**
* Representing city coordinates of given coordinates
* @property latitude The x value
* @property longitude The y value
*/
internal data class CityCoordinatesResponseModel(
    @SerializedName("lat")
    val latitude: Double,
    @SerializedName("lon")
    val longitude: Double
)
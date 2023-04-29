package me.learning.weathernotfound.domain.currentWeather.networkModels

import com.google.gson.annotations.SerializedName

/**
 * Representing city coordinates of given coordinates
 * @property latitude The x value
 * @property longitude The y value
 */
data class CityCoordinatesResponseModel(
    @SerializedName("lat")
    val latitude: Double,
    @SerializedName("lon")
    val longitude: Double
)
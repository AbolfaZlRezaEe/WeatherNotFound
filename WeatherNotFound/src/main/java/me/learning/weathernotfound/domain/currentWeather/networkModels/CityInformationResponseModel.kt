package me.learning.weathernotfound.domain.currentWeather.networkModels

import com.google.gson.annotations.SerializedName

/**
 * Representing city information of given coordinates
 * @property id Internal parameter represented by OpenWeatherMap
 * @property countryName The country name of given coordinates
 * @property sunrise The sunrise time, unix, UTC
 * @property sunset The sunset time, unix, UTC
 * @property type Internal parameter represented by OpenWeatherMap
 */
data class CityInformationResponseModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("country")
    val countryName: String,
    @SerializedName("sunrise")
    val sunrise: Int,
    @SerializedName("sunset")
    val sunset: Int,
    @SerializedName("type")
    val type: Int
)
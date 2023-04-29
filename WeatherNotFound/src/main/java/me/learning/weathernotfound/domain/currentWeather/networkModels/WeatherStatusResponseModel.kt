package me.learning.weathernotfound.domain.currentWeather.networkModels

import com.google.gson.annotations.SerializedName

/**
 * Representing weather status of given coordinates
 * @property id Internal parameter represented by OpenWeatherMap
 * @property status Group of weather parameters(Rain, Snow, Extreme, ect.)
 * @property description Weather condition within the group
 * @property icon The code that represent the icon of that status
 */
internal data class WeatherStatusResponseModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val status: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String,
)
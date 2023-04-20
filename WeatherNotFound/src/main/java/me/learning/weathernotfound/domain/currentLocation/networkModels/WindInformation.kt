package me.learning.weathernotfound.domain.currentLocation.networkModels

import com.google.gson.annotations.SerializedName

/**
 * Representing wind information of given coordinates
 * @property degree Wind direction
 * @property gust Wind gust
 * @property speed Wind speed
 */
data class WindInformation(
    @SerializedName("deg")
    val degree: Int,
    @SerializedName("gust")
    val gust: Double,
    @SerializedName("speed")
    val speed: Double
)
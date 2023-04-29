package me.learning.weathernotfound.domain.fiveDayThreeHourForecast.networkModels

import com.google.gson.annotations.SerializedName

/**
 * Representing wind information of given coordinates
 * @property degree Wind direction
 * @property gust Wind gust
 * @property speed Wind speed
 */
internal data class WindInformationResponseModel(
    @SerializedName("speed")
    val speed: Double,
    @SerializedName("degree")
    val degree: Int,
    @SerializedName("gust")
    val gust: Double
)
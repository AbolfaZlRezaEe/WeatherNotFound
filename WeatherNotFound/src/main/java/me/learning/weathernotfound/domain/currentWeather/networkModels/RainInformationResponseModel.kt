package me.learning.weathernotfound.domain.currentWeather.networkModels

import com.google.gson.annotations.SerializedName

/**
 * Representing rain information of given coordinates
 * NOTE: This information might be empty and depends on the city!
 * @property oneHour The 1 hour rain information of given coordinates
 * @property threeHour The 3 hour rain information of given coordinates
 */
internal data class RainInformationResponseModel(
    @SerializedName("1h")
    val oneHour: Double,
    @SerializedName("3h")
    val threeHour: Double,
)
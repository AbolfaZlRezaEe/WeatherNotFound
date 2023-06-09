package me.learning.weathernotfound.domain.currentWeather.networkModels

import com.google.gson.annotations.SerializedName

/**
 * Representing snow information of given coordinates
 * NOTE: This information might be empty and depends on the city!
 * @property oneHour The 1 hour snow information of given coordinates
 * @property threeHour The 3 hour snow information of given coordinates
 */
internal data class SnowInformationResponseModel(
    @SerializedName("1h")
    val oneHour: Double?,
    @SerializedName("3h")
    val threeHour: Double?,
)
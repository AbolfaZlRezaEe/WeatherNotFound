package me.learning.weathernotfound.domain.fiveDayThreeHourForecast.networkModels

import com.google.gson.annotations.SerializedName

/**
 * Representing snow information of given coordinates
 * NOTE: This information might be empty and depends on the city!
 * @property threeHour The 3 hour snow information of given coordinates
 */
data class SnowInformationResponseModel(
    @SerializedName("3h")
    val threeHour: Double
)
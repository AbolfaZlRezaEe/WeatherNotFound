package me.learning.weathernotfound.domain.fiveDayThreeHourForecast.networkModels

import com.google.gson.annotations.SerializedName

/**
 * Representing rain information of given coordinates
 * NOTE: This information might be empty and depends on the city!
 * @property threeHour The 3 hour rain information of given coordinates
 */
data class RainInformationResponseModel(
    @SerializedName("3h")
    val threeHour: Double?
)
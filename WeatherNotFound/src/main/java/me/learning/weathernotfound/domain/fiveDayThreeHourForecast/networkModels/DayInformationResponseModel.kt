package me.learning.weathernotfound.domain.fiveDayThreeHourForecast.networkModels

import com.google.gson.annotations.SerializedName

/**
 * Represent the day status.
 * @property dayStatus Can be night(n) or day(d).
 */
data class DayInformationResponseModel(
    @SerializedName("pod")
    val dayStatus: String
)
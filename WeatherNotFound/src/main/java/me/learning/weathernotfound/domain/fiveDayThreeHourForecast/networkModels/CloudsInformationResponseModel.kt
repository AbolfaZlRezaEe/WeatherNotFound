package me.learning.weathernotfound.domain.fiveDayThreeHourForecast.networkModels

import com.google.gson.annotations.SerializedName

/**
 * Representing cloud information of given coordinates
 * @property cloudiness The cloudiness in percentage
 */
data class CloudsInformationResponseModel(
    @SerializedName("all")
    val cloudiness: Int
)
package me.learning.weathernotfound.domain.currentWeather.networkModels

import com.google.gson.annotations.SerializedName

/**
 * Representing cloud information of given coordinates
 * @property cloudiness The cloudiness in percentage
 */
data class CloudInformationResponseModel(
    @SerializedName("all")
    val cloudiness: Int
)
package me.learning.weathernotfound.domain.currentLocation.networkModels

import com.google.gson.annotations.SerializedName

/**
 * Representing cloud information of given coordinates
 * @property cloudiness The cloudiness in percentage
 */
data class CloudInformation(
    @SerializedName("all")
    val cloudiness: Int
)
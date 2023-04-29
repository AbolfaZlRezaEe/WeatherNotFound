package me.learning.weathernotfound.domain.fiveDayThreeHourForecast.networkModels

import com.google.gson.annotations.SerializedName

/**
 * Represents the city information of given coordinates
 * @property id The city id of given coordinates
 * @property cityName
 * @property cityCoordinates
 * @property country The country code of given coordinates.
 * @property population
 * @property sunrise The sunrise time, unix, UTC
 * @property sunset The sunset time, unix, UTC
 * @property timezone Shift in seconds from UTC
 */
internal data class CityInformationResponseModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val cityName: String,
    @SerializedName("coord")
    val cityCoordinates: CityCoordinatesResponseModel,
    @SerializedName("country")
    val country: String,
    @SerializedName("population")
    val population: Int,
    @SerializedName("sunrise")
    val sunrise: Int,
    @SerializedName("sunset")
    val sunset: Int,
    @SerializedName("timezone")
    val timezone: Int
)
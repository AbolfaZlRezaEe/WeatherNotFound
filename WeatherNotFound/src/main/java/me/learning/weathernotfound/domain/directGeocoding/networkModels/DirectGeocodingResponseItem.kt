package me.learning.weathernotfound.domain.directGeocoding.networkModels

import com.google.gson.annotations.SerializedName

/**
 * All the coordinate information for given city name
 * @property coordinateName The name of that coordinate in english
 * @property countryName The name of country that coordinate is on
 * @property latitude The x coordinate of given coordinate
 * @property longitude The y coordinate of given coordinate
 * @property coordinateNameInLanguages The list of names of this coordinate in different languages
 * @see <a href="https://openweathermap.org/api/geocoding-api#direct">OpenWeatherMap api documentation</a>
 */
internal data class DirectGeocodingResponseItem(
    @SerializedName("name")
    val coordinateName: String,
    @SerializedName("country")
    val countryName: String,
    @SerializedName("lat")
    val latitude: Double,
    @SerializedName("lon")
    val longitude: Double,
    @SerializedName("local_names")
    val coordinateNameInLanguages: LocalNames?,
)
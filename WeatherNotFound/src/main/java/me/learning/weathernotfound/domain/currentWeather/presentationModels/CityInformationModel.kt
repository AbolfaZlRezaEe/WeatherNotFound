package me.learning.weathernotfound.domain.currentWeather.presentationModels

/**
 * Representing city information of given coordinates
 * @property id Internal parameter represented by OpenWeatherMap
 * @property countryName The country name of given coordinates
 * @property sunrise The sunrise time, unix, UTC
 * @property sunset The sunset time, unix, UTC
 * @property type Internal parameter represented by OpenWeatherMap
 */
data class CityInformationModel(
    val id: Int,
    val countryName: String,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
)
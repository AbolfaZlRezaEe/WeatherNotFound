package me.learning.weathernotfound.domain.fiveDayThreeHourForecast.presentationModels

/**
 * Representing weather status of given coordinates
 * @property id Internal parameter represented by OpenWeatherMap
 * @property status Group of weather parameters(Rain, Snow, Extreme, ect.)
 * @property description Weather condition within the group
 * @property icon The code that represent the icon of that status
 */
data class WeatherInformationModel(
    val id: Int,
    val status: String,
    val description: String,
    val icon: String
)
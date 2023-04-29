package me.learning.weathernotfound.domain.fiveDayThreeHourForecast.presentationModels

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
data class CityInformationModel(
    val id: Int,
    val cityName: String,
    val cityCoordinates: CityCoordinatesModel,
    val country: String,
    val population: Int,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int
)
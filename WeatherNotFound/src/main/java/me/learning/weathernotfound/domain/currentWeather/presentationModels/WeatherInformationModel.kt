package me.learning.weathernotfound.domain.currentWeather.presentationModels

/**
 * Representing weather information of given coordinates
 * @property feelsLike The human temperature perception of weather
 * @property groundLevelPressure Atmospheric pressure on the ground level
 * @property humidity
 * @property pressure
 * @property seaLevelPressure Atmospheric pressure on the sea level
 * @property temperature
 * @property maximumTemperature The maximum temperature of given coordinates
 * @property minimumTemperature The minimum temperature of given coordinates
 */
data class WeatherInformationModel(
    val feelsLike: Double,
    val groundLevelPressure: Int,
    val humidity: Int,
    val pressure: Int,
    val seaLevelPressure: Int,
    val temperature: Double,
    val maximumTemperature: Double,
    val minimumTemperature: Double
)
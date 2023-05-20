package me.learning.weathernotfound.domain.fiveDayThreeHourForecast.presentationModels

/**
 * Represents the weather condition.
 * @property temperature
 * @property feelsLike The human temperature perception of weather
 * @property maximumTemperature The maximum temperature of given coordinates
 * @property minimumTemperature The minimum temperature of given coordinates
 * @property pressure
 * @property seaLevelPressure Atmospheric pressure on the sea level
 * @property groundLevelPressure Atmospheric pressure on the ground level
 * @property humidity
 * @property tempKf Internal parameter represented by OpenWeatherMap
 */
data class WeatherConditionInformationModel(
    val temperature: Double,
    val feelsLike: Double,
    val minimumTemperature: Double,
    val maximumTemperature: Double,
    val pressure: Int,
    val seaLevelPressure: Int,
    val groundLevelPressure: Int,
    val humidity: Int,
    val tempKf: Double
)
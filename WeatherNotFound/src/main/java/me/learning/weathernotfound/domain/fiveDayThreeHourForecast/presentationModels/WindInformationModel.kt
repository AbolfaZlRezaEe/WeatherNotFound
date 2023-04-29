package me.learning.weathernotfound.domain.fiveDayThreeHourForecast.presentationModels

/**
 * Representing wind information of given coordinates
 * @property degree Wind direction
 * @property gust Wind gust
 * @property speed Wind speed
 */
data class WindInformationModel(
    val speed: Double,
    val degree: Int,
    val gust: Double
)
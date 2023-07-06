package me.learning.weathernotfound.domain.currentWeather.presentationModels

/**
 * Representing wind information of given coordinates
 * @property degree Wind direction
 * @property gust Wind gust
 * @property speed Wind speed
 */
data class WindInformationModel(
    val degree: Int?,
    val gust: Double?,
    val speed: Double?
)
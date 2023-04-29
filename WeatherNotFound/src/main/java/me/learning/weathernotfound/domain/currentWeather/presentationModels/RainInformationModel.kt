package me.learning.weathernotfound.domain.currentWeather.presentationModels

/**
 * Representing rain information of given coordinates
 * NOTE: This information might be empty and depends on the city!
 * @property oneHour The 1 hour rain information of given coordinates
 * @property threeHour The 3 hour rain information of given coordinates
 */
data class RainInformationModel(
    val oneHour: Double,
    val threeHour: Double,
)
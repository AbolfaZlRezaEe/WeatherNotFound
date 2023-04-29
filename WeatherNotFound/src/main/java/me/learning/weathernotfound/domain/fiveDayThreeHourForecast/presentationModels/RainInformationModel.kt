package me.learning.weathernotfound.domain.fiveDayThreeHourForecast.presentationModels

/**
 * Representing rain information of given coordinates
 * NOTE: This information might be empty and depends on the city!
 * @property threeHour The 3 hour rain information of given coordinates
 */
data class RainInformationModel(
    val threeHour: Double?
)
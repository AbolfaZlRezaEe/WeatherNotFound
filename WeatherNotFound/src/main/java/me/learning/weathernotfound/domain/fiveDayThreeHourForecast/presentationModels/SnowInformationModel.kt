package me.learning.weathernotfound.domain.fiveDayThreeHourForecast.presentationModels

/**
 * Representing snow information of given coordinates
 * NOTE: This information might be empty and depends on the city!
 * @property threeHour The 3 hour snow information of given coordinates
 */
data class SnowInformationModel(
    val threeHour: Double?
)
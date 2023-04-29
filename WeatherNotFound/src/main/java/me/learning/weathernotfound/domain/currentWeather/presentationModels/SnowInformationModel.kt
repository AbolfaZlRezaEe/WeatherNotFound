package me.learning.weathernotfound.domain.currentWeather.presentationModels

/**
 * Representing snow information of given coordinates
 * NOTE: This information might be empty and depends on the city!
 * @property oneHour The 1 hour snow information of given coordinates
 * @property threeHour The 3 hour snow information of given coordinates
 */
data class SnowInformationModel(
    val oneHour: Double?,
    val threeHour: Double?,
)
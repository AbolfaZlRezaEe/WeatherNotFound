package me.learning.weathernotfound.domain.fiveDayThreeHourForecast.presentationModels

/**
 * Representing cloud information of given coordinates
 * @property cloudiness The cloudiness in percentage
 */
data class CloudsInformationModel(
    val cloudiness: Int
)
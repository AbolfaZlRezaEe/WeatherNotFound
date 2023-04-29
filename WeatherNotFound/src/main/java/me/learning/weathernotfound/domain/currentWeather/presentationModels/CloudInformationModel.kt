package me.learning.weathernotfound.domain.currentWeather.presentationModels

/**
 * Representing cloud information of given coordinates
 * @property cloudiness The cloudiness in percentage
 */
data class CloudInformationModel(
    val cloudiness: Int
)
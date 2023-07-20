package me.learning.weathernotfound.domain.fiveDayThreeHourForecast.presentationModels

/**
 * Represents the day status.
 * @property dayStatus Can be night(n) or day(d).
 */
data class DayInformationModel(
    val dayStatus: String
)
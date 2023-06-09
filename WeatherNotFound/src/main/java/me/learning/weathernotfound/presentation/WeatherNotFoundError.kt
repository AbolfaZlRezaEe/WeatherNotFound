package me.learning.weathernotfound.presentation

data class WeatherNotFoundError(
    val httpResponseCode: Int = -1,
    val httpResponseMessage: String? = null,
    val exception: Exception? = null,
    val internalErrorMessage: String? = null
)

package me.learning.weathernotfound.presentation

data class WeatherNotFoundResponse<T>(
    val httpResponseCode: Int = -1,
    val responseType: ResponseType,
    val responseModel: T
)

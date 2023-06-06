package me.learning.weathernotfound.data.repository

internal data class WeatherNotFoundResponse<T>(
    val httpResponseCode: Int = -1,
    val responseType: ResponseType,
    val responseModel: T
)

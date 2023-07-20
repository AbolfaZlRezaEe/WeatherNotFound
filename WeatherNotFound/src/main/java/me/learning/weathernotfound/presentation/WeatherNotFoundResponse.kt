package me.learning.weathernotfound.presentation

/**
 * Represents the response which WeatherNotFound expose outside.
 *
 * @param httpResponseCode if the response type is [ResponseType.NETWORK], it will have the http response code,
 * otherwise it will be -1.
 * @param responseType
 * @param responseModel
 */
data class WeatherNotFoundResponse<T>(
    val httpResponseCode: Int = -1,
    val responseType: ResponseType,
    val responseModel: T
)

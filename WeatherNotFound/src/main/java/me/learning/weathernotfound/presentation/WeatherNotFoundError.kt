package me.learning.weathernotfound.presentation

/**
 * Represents the errors which WeatherNotFound expose outside.
 *
 * @param httpResponseCode if the response type is [ResponseType.NETWORK], it will have the http response code,
 * otherwise it will be -1.
 * @param httpResponseMessage if the response type is [ResponseType.NETWORK], it will have the http response message,
 * otherwise it will be null.
 * @param exception in some cases which requests face with problems in parsing, requesting or storing data,
 * this parameter will contain exception cause. otherwise it will be null.
 */
data class WeatherNotFoundError(
    val httpResponseCode: Int = -1,
    val httpResponseMessage: String? = null,
    val exception: Exception? = null,
)

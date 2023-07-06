package me.learning.weathernotfound.data.network.providers

import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Request

internal object RequestProvider {

    // Reverse Geocoding request params
    private const val REVERSE_GEOCODING_PARAM_LATITUDE = "lat"
    private const val REVERSE_GEOCODING_PARAM_LONGITUDE = "lon"
    private const val REVERSE_GEOCODING_PARAM_LIMIT = "limit"

    // Direct Geocoding request params
    private const val DIRECT_GEOCODING_PARAM_COORDINATE_NAME = "q"
    private const val DIRECT_GEOCODING_PARAM_LIMIT = "limit"

    // Current weather request params
    private const val CURRENT_WEATHER_PARAM_LATITUDE = "lat"
    private const val CURRENT_WEATHER_PARAM_LONGITUDE = "lon"

    // 5 days 3 hours request params
    private const val FIVE_DAY_WEATHER_PARAM_LATITUDE = "lat"
    private const val FIVE_DAY_WEATHER_PARAM_LONGITUDE = "lon"

    fun provideReverseGeocodingRequest(
        url: String,
        latitude: Double,
        longitude: Double,
        limit: Int,
    ): Request? {
        val httpUrl = url.toHttpUrlOrNull()?.newBuilder()
            ?.addQueryParameter(REVERSE_GEOCODING_PARAM_LATITUDE, latitude.toString())
            ?.addQueryParameter(REVERSE_GEOCODING_PARAM_LONGITUDE, longitude.toString())
            ?.addQueryParameter(REVERSE_GEOCODING_PARAM_LIMIT, limit.toString())
            ?.build() ?: return null

        return Request.Builder().url(httpUrl).build()
    }

    fun provideDirectGeocodingRequest(
        url: String,
        coordinateName: String,
        limit: Int,
    ): Request? {
        val httpUrl = url.toHttpUrlOrNull()?.newBuilder()
            ?.addQueryParameter(DIRECT_GEOCODING_PARAM_COORDINATE_NAME, coordinateName)
            ?.addQueryParameter(DIRECT_GEOCODING_PARAM_LIMIT, limit.toString())
            ?.build() ?: return null

        return Request.Builder().url(httpUrl).build()
    }

    fun provideCurrentWeatherRequest(
        url: String,
        latitude: Double,
        longitude: Double,
    ): Request? {
        val httpUrl = url.toHttpUrlOrNull()?.newBuilder()
            ?.addQueryParameter(CURRENT_WEATHER_PARAM_LATITUDE, latitude.toString())
            ?.addQueryParameter(CURRENT_WEATHER_PARAM_LONGITUDE, longitude.toString())
            ?.build() ?: return null

        return Request.Builder().url(httpUrl).build()
    }

    fun provideFiveDayThreeHourForecastRequest(
        url: String,
        latitude: Double,
        longitude: Double
    ): Request? {
        val httpUrl = url.toHttpUrlOrNull()?.newBuilder()
            ?.addQueryParameter(FIVE_DAY_WEATHER_PARAM_LATITUDE, latitude.toString())
            ?.addQueryParameter(FIVE_DAY_WEATHER_PARAM_LONGITUDE, longitude.toString())
            ?.build() ?: return null

        return Request.Builder().url(httpUrl).build()
    }
}
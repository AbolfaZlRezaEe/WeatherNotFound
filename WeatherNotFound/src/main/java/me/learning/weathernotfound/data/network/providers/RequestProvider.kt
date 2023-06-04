package me.learning.weathernotfound.data.network.providers

import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Request

internal object RequestProvider {

    // Geocoding request params
    private const val GEOCODING_PARAM_LATITUDE = "lat"
    private const val GEOCODING_PARAM_LONGITUDE = "lon"
    private const val GEOCODING_PARAM_LIMIT = "limit"

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
            ?.addQueryParameter(GEOCODING_PARAM_LATITUDE, latitude.toString())
            ?.addQueryParameter(GEOCODING_PARAM_LONGITUDE, longitude.toString())
            ?.addQueryParameter(GEOCODING_PARAM_LIMIT, limit.toString())
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
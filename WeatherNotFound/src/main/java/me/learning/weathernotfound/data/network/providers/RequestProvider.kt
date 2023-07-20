package me.learning.weathernotfound.data.network.providers

import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * Create and provide all requests that **WeatherNotFound** will use.
 */
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

    /**
     * Create reverseGeocoding request using query parameters you specified.
     *
     * @param url the url which is necessary for serving reverse geocoding request.
     * @param latitude
     * @param longitude
     * @param limit
     *
     * @return an object of [Request] which will be used for requesting with [OkHttpClient], null
     * if anything happen during creation of url!
     */
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

    /**
     * Create directGeocoding request using query parameters you specified.
     *
     * @param url the url which is necessary for serving directGeocoding request.
     * @param coordinateName
     * @param limit
     *
     * @return an object of [Request] which will be used for requesting with [OkHttpClient], null
     * if anything happen during creation of url!
     */
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

    /**
     * Create currentWeather request using query parameters you specified.
     *
     * @param url the url which is necessary for serving currentWeather request.
     * @param latitude
     * @param longitude
     *
     * @return an object of [Request] which will be used for requesting with [OkHttpClient], null
     * if anything happen during creation of url!
     */
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

    /**
     * Create 5 day 3 hour forecast request using query parameters you specified.
     *
     * @param url the url which is necessary for serving 5 day 3 hour forecast request.
     * @param latitude
     * @param longitude
     *
     * @return an object of [Request] which will be used for requesting with [OkHttpClient], null
     * if anything happen during creation of url!
     */
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
package me.learning.weathernotfound.data.network.providers

/**
 * Provide all URLs we use in **WeatherNotFound**.
 */
internal object UrlProvider {
    private const val OPEN_WEATHER_BASE_URL = "https://api.openweathermap.org"

    const val REVERSE_GEOCODING_URL = "${OPEN_WEATHER_BASE_URL}/geo/1.0/reverse"
    const val DIRECT_GEOCODING_URL = "${OPEN_WEATHER_BASE_URL}/geo/1.0/direct"

    const val CURRENT_WEATHER_URL = "${OPEN_WEATHER_BASE_URL}/data/2.5/weather"
    const val FIVE_DAY_THREE_HOUR_FORECAST_URL = "${OPEN_WEATHER_BASE_URL}/data/2.5/forecast"
}
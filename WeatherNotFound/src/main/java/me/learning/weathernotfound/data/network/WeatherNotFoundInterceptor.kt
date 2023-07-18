package me.learning.weathernotfound.data.network

import me.learning.weathernotfound.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

internal class WeatherNotFoundInterceptor : Interceptor {
    companion object {
        private const val HEADER_KEY_LANGUAGE = "lang"
        private const val HEADER_KEY_MODE = "mode"
        private const val HEADER_KEY_API_KEY = "appid"
        private const val HEADER_KEY_UNITS = "units"

        private const val HEADER_VALUE_DEFAULT_RESPONSE_FORMAT = "json"
        private const val HEADER_VALUE_DEFAULT_LANGUAGE = "en"
        private const val HEADER_VALUE_DEFAULT_RESPONSE_UNIT = "metric"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest = chain.request()

        val newHttpURL = oldRequest.url.newBuilder()
                .addQueryParameter(HEADER_KEY_API_KEY, BuildConfig.OpenWeatherApiKey)
                .build()

        var languageHeaderValue = BuildConfig.OpenWeatherResponseLanguage
        if (languageHeaderValue.isEmpty() || languageHeaderValue.lowercase() == "null") {
            languageHeaderValue = HEADER_VALUE_DEFAULT_LANGUAGE
        }

        var responseUnitHeaderValue = BuildConfig.OpenWeatherResponseUnit
        if (responseUnitHeaderValue.isEmpty() || languageHeaderValue.lowercase() == "null") {
            responseUnitHeaderValue = HEADER_VALUE_DEFAULT_RESPONSE_UNIT
        }

        var responseFormatHeaderValue = BuildConfig.OpenWeatherResponseFormat
        if (responseFormatHeaderValue.isEmpty() || languageHeaderValue.lowercase() == "null") {
            responseFormatHeaderValue = HEADER_VALUE_DEFAULT_RESPONSE_FORMAT
        }

        val newRequest = oldRequest.newBuilder()
                .addHeader(HEADER_KEY_MODE, responseFormatHeaderValue)
                .addHeader(HEADER_KEY_LANGUAGE, languageHeaderValue)
                .addHeader(HEADER_KEY_UNITS, responseUnitHeaderValue)
                .url(newHttpURL)
                .build()

        return chain.proceed(newRequest)
    }
}
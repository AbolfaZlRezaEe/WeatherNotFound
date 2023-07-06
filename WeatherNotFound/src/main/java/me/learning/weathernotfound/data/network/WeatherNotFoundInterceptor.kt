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

        private const val HEADER_VALUE_MODE = "json"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest = chain.request()

        val newHttpURL = oldRequest.url.newBuilder()
            .addQueryParameter(HEADER_KEY_API_KEY, BuildConfig.OpenWeatherApiKey)
            .build()

        val newRequest = oldRequest.newBuilder()
            .addHeader(HEADER_KEY_MODE, HEADER_VALUE_MODE)
            .addHeader(HEADER_KEY_LANGUAGE, BuildConfig.OpenWeatherResponseLanguage)
            .addHeader(HEADER_KEY_UNITS, BuildConfig.OpenWeatherResponseUnit)
            .url(newHttpURL)
            .build()

        return chain.proceed(newRequest)
    }
}
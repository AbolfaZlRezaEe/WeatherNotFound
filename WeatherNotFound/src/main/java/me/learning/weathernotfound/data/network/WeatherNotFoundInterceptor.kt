package me.learning.weathernotfound.data.network

import me.learning.weathernotfound.presentation.WeatherNotFound
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Gateway of all requests in **WeatherNotFound**. Every request will passes through this class
 * to add necessary **Headers** or **Query Parameters** to it.
 */
internal class WeatherNotFoundInterceptor : Interceptor {
    companion object {
        private const val HEADER_KEY_LANGUAGE = "lang"
        private const val HEADER_KEY_MODE = "mode"
        private const val HEADER_KEY_API_KEY = "appid"
        private const val HEADER_KEY_UNITS = "units"
    }

    /**
     * NOTE: all requests will edited and add these headers into it:
     * 1. [HEADER_KEY_API_KEY]: Which necessary for all requests. you should create your token in OpenWeatherMap panel!
     * 2. [HEADER_KEY_LANGUAGE]: It's not necessary, you can set it for your purpose. default it will be **en**
     * 3. [HEADER_KEY_UNITS]: It's not necessary, you can set it for your purpose. default it will be **metric**
     * 4. [HEADER_KEY_MODE]: For now, we can support just **json**.
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest = chain.request()

        val newHttpURL = oldRequest.url.newBuilder()
            .addQueryParameter(HEADER_KEY_API_KEY, WeatherNotFound.OPEN_WEATHER_API_KEY)
            .build()

        val newRequest = oldRequest.newBuilder()
            .addHeader(HEADER_KEY_MODE, WeatherNotFound.OPEN_WEATHER_RESPONSE_FORMAT)
            .addHeader(HEADER_KEY_LANGUAGE, WeatherNotFound.OPEN_WEATHER_RESPONSE_LANGUAGE)
            .addHeader(HEADER_KEY_UNITS, WeatherNotFound.OPEN_WEATHER_RESPONSE_UNIT)
            .url(newHttpURL)
            .build()

        return chain.proceed(newRequest)
    }
}
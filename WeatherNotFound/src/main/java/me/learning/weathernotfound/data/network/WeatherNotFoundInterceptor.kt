package me.learning.weathernotfound.data.network

import me.learning.weathernotfound.BuildConfig
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

        private const val HEADER_VALUE_DEFAULT_RESPONSE_FORMAT = "json"
        private const val HEADER_VALUE_DEFAULT_LANGUAGE = "en"
        private const val HEADER_VALUE_DEFAULT_RESPONSE_UNIT = "metric"
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

        // Todo: For now, SDK can just support Json responses! Other formats will be added soon.
        /*var responseFormatHeaderValue = BuildConfig.OpenWeatherResponseFormat
        if (responseFormatHeaderValue.isEmpty() || languageHeaderValue.lowercase() == "null") {
            responseFormatHeaderValue = HEADER_VALUE_DEFAULT_RESPONSE_FORMAT
        }*/

        val responseFormatHeaderValue = HEADER_VALUE_DEFAULT_RESPONSE_FORMAT

        val newRequest = oldRequest.newBuilder()
                .addHeader(HEADER_KEY_MODE, responseFormatHeaderValue)
                .addHeader(HEADER_KEY_LANGUAGE, languageHeaderValue)
                .addHeader(HEADER_KEY_UNITS, responseUnitHeaderValue)
                .url(newHttpURL)
                .build()

        return chain.proceed(newRequest)
    }
}
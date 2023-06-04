package me.learning.weathernotfound.data.network.providers

import me.learning.weathernotfound.data.network.WeatherNotFoundInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

internal object NetworkInterfaceProvider {
    private const val OKHTTP_NORMAL_READ_TIMEOUT = 5L // Seconds
    private const val OKHTTP_NORMAL_CONNECT_TIMEOUT = 5L // Seconds
    private val OKHTTP_NORMAL_LOGGING_INTERCEPTOR = HttpLoggingInterceptor.Level.NONE

    private var OKHTTP_CLIENT_NORMAL_INSTANCE: OkHttpClient? = null
    private var OKHTTP_CLIENT_CUSTOM_INSTANCE: OkHttpClient? = null

    @Synchronized
    fun init(
        httpLoggingLevel: HttpLoggingInterceptor.Level = OKHTTP_NORMAL_LOGGING_INTERCEPTOR
    ) {
        if (OKHTTP_CLIENT_NORMAL_INSTANCE == null) {
            OKHTTP_CLIENT_NORMAL_INSTANCE = OkHttpClient.Builder()
                .connectTimeout(timeout = OKHTTP_NORMAL_CONNECT_TIMEOUT, unit = TimeUnit.SECONDS)
                .readTimeout(timeout = OKHTTP_NORMAL_READ_TIMEOUT, unit = TimeUnit.SECONDS)
                .addInterceptor(getLoggingInterceptor(httpLoggingLevel))
                .addInterceptor(getWeatherNotFoundInterceptor())
                .build()
        }
    }

    @Synchronized
    fun initManually(
        httpLoggingLevel: HttpLoggingInterceptor.Level,
        readTimeoutInSeconds: Long,
        connectTimeoutInSeconds: Long
    ) {
        OKHTTP_CLIENT_CUSTOM_INSTANCE = OkHttpClient.Builder()
            .connectTimeout(timeout = connectTimeoutInSeconds, unit = TimeUnit.SECONDS)
            .readTimeout(timeout = readTimeoutInSeconds, unit = TimeUnit.SECONDS)
            .addInterceptor(getLoggingInterceptor(httpLoggingLevel))
            .addInterceptor(getWeatherNotFoundInterceptor())
            .build()
    }

    fun getOkHttpClient(): OkHttpClient {
        return if (OKHTTP_CLIENT_CUSTOM_INSTANCE != null) {
            OKHTTP_CLIENT_CUSTOM_INSTANCE!!
        } else {
            if (OKHTTP_CLIENT_NORMAL_INSTANCE == null) {
                init()
            }
            OKHTTP_CLIENT_NORMAL_INSTANCE!!
        }
    }

    private fun getLoggingInterceptor(
        httpLoggingLevel: HttpLoggingInterceptor.Level
    ): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(level = httpLoggingLevel)
    }

    private fun getWeatherNotFoundInterceptor(): WeatherNotFoundInterceptor =
        WeatherNotFoundInterceptor()
}
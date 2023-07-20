package me.learning.weathernotfound.data.network.providers

import com.google.gson.Gson
import me.learning.weathernotfound.data.network.WeatherNotFoundInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * Provide and manage everything that are related to the network interface in library.
 */
internal object NetworkInterfaceProvider {
    private const val OKHTTP_NORMAL_READ_TIMEOUT = 5L // Seconds
    private const val OKHTTP_NORMAL_CONNECT_TIMEOUT = 5L // Seconds
    private val OKHTTP_NORMAL_LOGGING_INTERCEPTOR = HttpLoggingInterceptor.Level.NONE

    private var OKHTTP_CLIENT_NORMAL_INSTANCE: OkHttpClient? = null
    private var OKHTTP_CLIENT_CUSTOM_INSTANCE: OkHttpClient? = null

    private var GSON_INSTANCE: Gson? = null

    const val NETWORK_AUTHORIZED_FAILED_HTTP_CODE = 401

    const val GEOCODING_LIMIT_REQUEST_PARAMETER_VALUE = 10

    /**
     * Instantiate singleton instance of [OkHttpClient] with default configuration that library specified before.
     *
     * @param httpLoggingLevel specify the level of logging for requests.
     * default value will be HttpLoggingInterceptor.Level.NONE
     */
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

    /**
     * Instantiate singleton instance of [OkHttpClient] with custom configuration that developer specified.
     *
     * @param [httpLoggingLevel] specify the level of logging for requests.
     * @param [readTimeoutInSeconds] specify the read timeout for requests.
     * @param [connectTimeoutInSeconds] specify the connect timeout for requests.
     */
    @Synchronized
    fun initManually(
        httpLoggingLevel: HttpLoggingInterceptor.Level?,
        readTimeoutInSeconds: Long?,
        connectTimeoutInSeconds: Long?,
    ) {
        OKHTTP_CLIENT_CUSTOM_INSTANCE = OkHttpClient.Builder()
            .connectTimeout(
                timeout = connectTimeoutInSeconds ?: OKHTTP_NORMAL_CONNECT_TIMEOUT,
                unit = TimeUnit.SECONDS
            )
            .readTimeout(
                timeout = readTimeoutInSeconds ?: OKHTTP_NORMAL_READ_TIMEOUT,
                unit = TimeUnit.SECONDS
            )
            .addInterceptor(
                getLoggingInterceptor(
                    httpLoggingLevel ?: OKHTTP_NORMAL_LOGGING_INTERCEPTOR
                )
            )
            .addInterceptor(getWeatherNotFoundInterceptor())
            .build()
    }

    /**
     * Instantiate singleton instance of Gson converter for requests.
     */
    @Synchronized
    fun initGsonConverter() {
        if (GSON_INSTANCE == null) {
            GSON_INSTANCE = Gson()
        }
    }

    /**
     * @return the instance of Gson created before using [initGsonConverter].
     */
    fun getGsonConverter(): Gson {
        if (GSON_INSTANCE == null) {
            initGsonConverter()
        }
        return GSON_INSTANCE!!
    }

    /**
     * @return the instance of **Custom OkHttpClient** which created before using [initManually],
     * otherwise it will return the **Normal OkHttpClient** even if it didn't instantiate before!
     */
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

    /**
     * @param httpLoggingLevel the level you want for logging requests.
     *
     * @return create and return a [HttpLoggingInterceptor] with the level you specified.
     */
    private fun getLoggingInterceptor(
        httpLoggingLevel: HttpLoggingInterceptor.Level
    ): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(level = httpLoggingLevel)
    }

    /**
     * @return an instance of [WeatherNotFoundInterceptor].
     */
    private fun getWeatherNotFoundInterceptor(): WeatherNotFoundInterceptor =
        WeatherNotFoundInterceptor()
}
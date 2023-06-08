package me.learning.weathernotfound.data.repository.provider

import me.learning.weathernotfound.data.local.LocalInterfaceProvider
import me.learning.weathernotfound.data.network.providers.NetworkInterfaceProvider
import me.learning.weathernotfound.data.repository.currentWeather.CurrentWeatherRepository
import me.learning.weathernotfound.data.repository.currentWeather.CurrentWeatherRepositoryImpl
import me.learning.weathernotfound.data.repository.fiveDayThreeHour.FiveDayThreeHourForecastRepository
import me.learning.weathernotfound.data.repository.fiveDayThreeHour.FiveDayThreeHourForecastRepositoryImpl
import me.learning.weathernotfound.data.repository.reverseGeocoding.ReverseGeocodingRepository
import me.learning.weathernotfound.data.repository.reverseGeocoding.ReverseGeocodingRepositoryImpl

internal object RepositoryProvider {
    private var reverseGeocodingRepository: ReverseGeocodingRepository? = null
    private var currentWeatherRepository: CurrentWeatherRepository? = null
    private var fiveDayThreeHourForecastRepository: FiveDayThreeHourForecastRepository? = null

    @Synchronized
    fun getReverseRepository(): ReverseGeocodingRepository {
        if (reverseGeocodingRepository == null) {
            reverseGeocodingRepository = ReverseGeocodingRepositoryImpl(
                reverseGeocodingDao = LocalInterfaceProvider
                    .getDatabaseInstance()
                    .reverseGeocodingDao(),
                okHttpClient = NetworkInterfaceProvider.getOkHttpClient(),
                gsonConverter = NetworkInterfaceProvider.getGsonConverter(),
            )
        }
        return reverseGeocodingRepository!!
    }

    @Synchronized
    fun getCurrentWeatherRepository(): CurrentWeatherRepository {
        if (currentWeatherRepository == null) {
            currentWeatherRepository = CurrentWeatherRepositoryImpl(
                currentWeatherDao = LocalInterfaceProvider
                    .getDatabaseInstance()
                    .currentWeatherDao(),
                okHttpClient = NetworkInterfaceProvider.getOkHttpClient(),
                gsonConverter = NetworkInterfaceProvider.getGsonConverter(),
            )
        }
        return currentWeatherRepository!!
    }

    @Synchronized
    fun getFiveDayThreeHourForecastRepository(): FiveDayThreeHourForecastRepository {
        if (fiveDayThreeHourForecastRepository == null) {
            fiveDayThreeHourForecastRepository = FiveDayThreeHourForecastRepositoryImpl(
                fiveDayThreeHourDao = LocalInterfaceProvider
                    .getDatabaseInstance()
                    .fiveDayThreeHourDao(),
                okHttpClient = NetworkInterfaceProvider.getOkHttpClient(),
                gsonConverter = NetworkInterfaceProvider.getGsonConverter()
            )
        }
        return fiveDayThreeHourForecastRepository!!
    }
}
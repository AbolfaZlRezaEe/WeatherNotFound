package me.learning.weathernotfound.data.repository.provider

import me.learning.weathernotfound.data.local.LocalInterfaceProvider
import me.learning.weathernotfound.data.network.providers.NetworkInterfaceProvider
import me.learning.weathernotfound.data.repository.currentWeather.CurrentWeatherRepository
import me.learning.weathernotfound.data.repository.currentWeather.CurrentWeatherRepositoryImpl
import me.learning.weathernotfound.data.repository.directGeocoding.DirectGeocodingRepository
import me.learning.weathernotfound.data.repository.directGeocoding.DirectGeocodingRepositoryImpl
import me.learning.weathernotfound.data.repository.fiveDayThreeHour.FiveDayThreeHourForecastRepository
import me.learning.weathernotfound.data.repository.fiveDayThreeHour.FiveDayThreeHourForecastRepositoryImpl
import me.learning.weathernotfound.data.repository.reverseGeocoding.ReverseGeocodingRepository
import me.learning.weathernotfound.data.repository.reverseGeocoding.ReverseGeocodingRepositoryImpl

internal object RepositoryProvider {
    private var reverseGeocodingRepository: ReverseGeocodingRepository? = null
    private var directGeocodingRepository: DirectGeocodingRepository? = null
    private var currentWeatherRepository: CurrentWeatherRepository? = null
    private var fiveDayThreeHourForecastRepository: FiveDayThreeHourForecastRepository? = null

    /**
     * Create and provider singleton instance of ReverseGeocodingRepository
     */
    @Synchronized
    fun getReverseRepository(): ReverseGeocodingRepository {
        if (reverseGeocodingRepository == null) {
            reverseGeocodingRepository = ReverseGeocodingRepositoryImpl(
                reverseGeocodingDao = LocalInterfaceProvider
                    .getDatabaseInstance()
                    ?.reverseGeocodingDao(),
                okHttpClient = NetworkInterfaceProvider.getOkHttpClient(),
                gsonConverter = NetworkInterfaceProvider.getGsonConverter(),
            )
        }
        return reverseGeocodingRepository!!
    }

    /**
     * Create and provider singleton instance of DirectGeocodingRepository
     */
    @Synchronized
    fun getDirectRepository(): DirectGeocodingRepository {
        if (directGeocodingRepository == null) {
            directGeocodingRepository = DirectGeocodingRepositoryImpl(
                directGeocodingDao = LocalInterfaceProvider
                    .getDatabaseInstance()
                    ?.directGeocodingDao(),
                okHttpClient = NetworkInterfaceProvider.getOkHttpClient(),
                gsonConverter = NetworkInterfaceProvider.getGsonConverter(),
            )
        }
        return directGeocodingRepository!!
    }

    /**
     * Create and provider singleton instance of CurrentWeatherRepository
     */
    @Synchronized
    fun getCurrentWeatherRepository(): CurrentWeatherRepository {
        if (currentWeatherRepository == null) {
            currentWeatherRepository = CurrentWeatherRepositoryImpl(
                currentWeatherDao = LocalInterfaceProvider
                    .getDatabaseInstance()
                    ?.currentWeatherDao(),
                okHttpClient = NetworkInterfaceProvider.getOkHttpClient(),
                gsonConverter = NetworkInterfaceProvider.getGsonConverter(),
            )
        }
        return currentWeatherRepository!!
    }

    /**
     * Create and provider singleton instance of FiveDayThreeHourForecastRepository
     */
    @Synchronized
    fun getFiveDayThreeHourForecastRepository(): FiveDayThreeHourForecastRepository {
        if (fiveDayThreeHourForecastRepository == null) {
            fiveDayThreeHourForecastRepository = FiveDayThreeHourForecastRepositoryImpl(
                fiveDayThreeHourDao = LocalInterfaceProvider
                    .getDatabaseInstance()
                    ?.fiveDayThreeHourDao(),
                okHttpClient = NetworkInterfaceProvider.getOkHttpClient(),
                gsonConverter = NetworkInterfaceProvider.getGsonConverter()
            )
        }
        return fiveDayThreeHourForecastRepository!!
    }

    /**
     * @return the actual value state of [reverseGeocodingRepository]. In case you haven't called [getReverseRepository]
     * function anywhere, this function will return null as the result. otherwise it can not be null!
     *
     * NOTE: you should not use this function in case of requesting or asking data from repository! this
     * function just created for use case of calling dispose function.
     */
    fun getActualReverseRepositoryValue(): ReverseGeocodingRepository? = reverseGeocodingRepository

    /**
     * @return the actual value state of [directGeocodingRepository]. In case you haven't called [getDirectRepository]
     * function anywhere, this function will return null as the result. otherwise it can not be null!
     *
     * NOTE: you should not use this function in case of requesting or asking data from repository! this
     * function just created for use case of calling dispose function.
     */
    fun getActualDirectRepository(): DirectGeocodingRepository? = directGeocodingRepository

    /**
     * @return the actual value state of [currentWeatherRepository]. In case you haven't called [getCurrentWeatherRepository]
     * function anywhere, this function will return null as the result. otherwise it can not be null!
     *
     * NOTE: you should not use this function in case of requesting or asking data from repository! this
     * function just created for use case of calling dispose function.
     */
    fun getActualCurrentWeatherRepository(): CurrentWeatherRepository? = currentWeatherRepository

    /**
     * @return the actual value state of [fiveDayThreeHourForecastRepository]. In case you haven't called [getFiveDayThreeHourForecastRepository]
     * function anywhere, this function will return null as the result. otherwise it can not be null!
     *
     * NOTE: you should not use this function in case of requesting or asking data from repository! this
     * function just created for use case of calling dispose function.
     */
    fun getActualFiveDayThreeHourRepository(): FiveDayThreeHourForecastRepository? =
        fiveDayThreeHourForecastRepository
}
package me.learning.weathernotfound.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.learning.weathernotfound.data.local.dao.CurrentWeatherDao
import me.learning.weathernotfound.data.local.dao.DirectGeocodingDao
import me.learning.weathernotfound.data.local.dao.FiveDayThreeHourDao
import me.learning.weathernotfound.data.local.dao.ReverseGeocodingDao
import me.learning.weathernotfound.domain.currentWeather.databaseModels.CurrentWeatherEntity
import me.learning.weathernotfound.domain.currentWeather.databaseModels.WeatherStatusEntity
import me.learning.weathernotfound.domain.directGeocoding.databaseModels.DirectGeocodingEntity
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.databaseModels.FiveDayThreeHourForecastEntity
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.databaseModels.ForecastEntity
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.databaseModels.WeatherInformationEntity
import me.learning.weathernotfound.domain.reverseGeocoding.databaseModels.ReverseGeocodingEntity

/**
 * Provide all configurations for database of **WeatherNotFound** in the library.
 * NOTE: **exportSchema** will be read from properties file which developer specified, default value will be false.
 * 1. **[CurrentWeatherEntity]:** Store all responses of current weather information.
 * 2. **[WeatherStatusEntity]:** Store the weather information of specific [CurrentWeatherEntity] object.
 * 3. **[FiveDayThreeHourForecastEntity]:** Store all responses of 5 day 3 hour forecast information.
 * 4. **[ForecastEntity]:** Store the forecast information of specific [FiveDayThreeHourForecastEntity] object.
 * 5. **[WeatherInformationEntity]:** Store the weather information of specific [ForecastEntity] object.
 * 6. **[ReverseGeocodingEntity]:** Store all responses of reverse geocoding information.
 * 7. **[DirectGeocodingEntity]:** Store all responses of direct geocoding information.
 */
@Database(
    entities = [
        CurrentWeatherEntity::class,
        WeatherStatusEntity::class,
        FiveDayThreeHourForecastEntity::class,
        ForecastEntity::class,
        WeatherInformationEntity::class,
        ReverseGeocodingEntity::class,
        DirectGeocodingEntity::class,
    ],
    version = LocalInterfaceProvider.DATABASE_VERSION,
    exportSchema = false,
)
@TypeConverters(DatabaseTypeConverter::class)
internal abstract class WeatherNotFoundDatabase : RoomDatabase() {

    abstract fun currentWeatherDao(): CurrentWeatherDao

    abstract fun fiveDayThreeHourDao(): FiveDayThreeHourDao

    abstract fun reverseGeocodingDao(): ReverseGeocodingDao

    abstract fun directGeocodingDao(): DirectGeocodingDao
}
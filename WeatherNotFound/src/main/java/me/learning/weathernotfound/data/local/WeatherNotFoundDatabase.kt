package me.learning.weathernotfound.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.learning.weathernotfound.data.local.dao.CurrentWeatherDao
import me.learning.weathernotfound.data.local.dao.FiveDayThreeHourDao
import me.learning.weathernotfound.data.local.dao.ReverseGeocodingDao
import me.learning.weathernotfound.domain.currentWeather.databaseModels.CurrentWeatherEntity
import me.learning.weathernotfound.domain.currentWeather.databaseModels.WeatherStatusEntity
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.databaseModels.FiveDayThreeHourForecastEntity
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.databaseModels.ForecastEntity
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.databaseModels.WeatherInformationEntity
import me.learning.weathernotfound.domain.reverseGeocoding.databaseModels.ReverseGeocodingEntity

@Database(
    entities = [
        CurrentWeatherEntity::class,
        WeatherStatusEntity::class,
        FiveDayThreeHourForecastEntity::class,
        ForecastEntity::class,
        WeatherInformationEntity::class,
        ReverseGeocodingEntity::class
    ],
    version = LocalInterfaceProvider.DATABASE_VERSION,
    exportSchema = true,
)
@TypeConverters(DatabaseTypeConverter::class)
internal abstract class WeatherNotFoundDatabase : RoomDatabase() {

    abstract fun currentWeatherDao(): CurrentWeatherDao

    abstract fun fiveDayThreeHourDao(): FiveDayThreeHourDao

    abstract fun reverseGeocodingDao(): ReverseGeocodingDao
}
package me.learning.weathernotfound.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import me.learning.weathernotfound.data.local.dao.CurrentWeatherDao
import me.learning.weathernotfound.data.local.dao.FiveDayThreeHourDao
import me.learning.weathernotfound.data.local.dao.ReverseGeocodingDao
import me.learning.weathernotfound.domain.currentWeather.databaseModels.CurrentWeatherEntity
import me.learning.weathernotfound.domain.currentWeather.databaseModels.WeatherStatusEntity
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.databaseModels.FiveDayThreeHourForecastEntity
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.databaseModels.ForecastEntity
import me.learning.weathernotfound.domain.reverseGeocoding.databaseModels.ReverseGeocodingEntity

@Database(
    entities = [
        CurrentWeatherEntity::class,
        WeatherStatusEntity::class,
        FiveDayThreeHourForecastEntity::class,
        ForecastEntity::class,
        me.learning.weathernotfound.domain.fiveDayThreeHourForecast.databaseModels.WeatherStatusEntity::class,
        ReverseGeocodingEntity::class
    ],
    version = DatabaseInterfaceProvider.DATABASE_VERSION,
    exportSchema = true,
)
internal abstract class WeatherNotFoundDatabase : RoomDatabase() {

    abstract fun currentWeatherDao(): CurrentWeatherDao

    abstract fun fiveDayThreeHourDao(): FiveDayThreeHourDao

    abstract fun reverseGeocodingDao(): ReverseGeocodingDao
}
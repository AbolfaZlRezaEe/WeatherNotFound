package me.learning.weathernotfound.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.learning.weathernotfound.domain.currentWeather.databaseModels.CurrentWeatherEntity
import me.learning.weathernotfound.domain.currentWeather.databaseModels.WeatherStatusEntity

@Dao
internal interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeatherEntity(currentWeatherEntity: CurrentWeatherEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllWeatherStatusEntities(weatherStatusEntities: List<WeatherStatusEntity>): List<Long>

    @Query("SELECT * FROM tbl_current_weather WHERE latitude =:latitude AND longitude =:longitude")
    suspend fun getCurrentWeatherEntityByCoordinates(latitude: Double, longitude: Double)

    @Query("SELECT * FROM tbl_weather_status WHERE current_weather_id =:currentWeatherId")
    suspend fun getWeatherStatusesByCurrentWeatherId(currentWeatherId: Int)
}
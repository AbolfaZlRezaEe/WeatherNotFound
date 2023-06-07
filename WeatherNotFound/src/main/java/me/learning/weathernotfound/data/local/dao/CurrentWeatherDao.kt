package me.learning.weathernotfound.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import me.learning.weathernotfound.domain.currentWeather.databaseModels.CurrentWeatherEntity
import me.learning.weathernotfound.domain.currentWeather.databaseModels.WeatherStatusEntity

@Dao
internal interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeatherEntity(currentWeatherEntity: CurrentWeatherEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllWeatherStatusEntities(weatherStatusEntities: List<WeatherStatusEntity>): List<Long>

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateCurrentWeatherEntity(currentWeatherEntity: CurrentWeatherEntity)

    @Query("SELECT * FROM tbl_current_weather WHERE latitude =:latitude AND longitude =:longitude")
    suspend fun getCurrentWeatherEntityByCoordinates(
        latitude: Double,
        longitude: Double
    ): CurrentWeatherEntity?

    @Query("SELECT * FROM tbl_weather_status WHERE current_weather_id =:currentWeatherId")
    suspend fun getWeatherStatusesByCurrentWeatherId(currentWeatherId: Long): List<WeatherStatusEntity>

    @Query("DELETE FROM tbl_weather_status WHERE current_weather_id =:currentWeatherId")
    suspend fun deleteWeatherStatuesByCurrentWeatherId(currentWeatherId: Long)

    @Query("DELETE FROM tbl_current_weather WHERE updated_at<=:selectedTimeStamp")
    suspend fun deleteCurrentWeatherEntitiesOlderThan(selectedTimeStamp: Long)

    @Query("DELETE FROM tbl_current_weather")
    suspend fun invalidateCache()
}
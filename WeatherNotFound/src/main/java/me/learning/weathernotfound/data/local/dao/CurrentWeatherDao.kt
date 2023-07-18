package me.learning.weathernotfound.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import me.learning.weathernotfound.domain.currentWeather.databaseModels.CurrentWeatherEntity
import me.learning.weathernotfound.domain.currentWeather.databaseModels.WeatherStatusEntity

/**
 * Provide everything you need to access and operate on CurrentWeather information
 */
@Dao
internal interface CurrentWeatherDao {

    /**
     * Insert a [CurrentWeatherEntity] model into the database.
     * NOTE: if the given [CurrentWeatherEntity] is duplicate, it will replaced!
     * @return auto-generated id from Room database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeatherEntity(currentWeatherEntity: CurrentWeatherEntity): Long

    /**
     * Insert list of [WeatherStatusEntity] models that is related to a [CurrentWeatherEntity] into the database.
     * NOTE: if the given [WeatherStatusEntity] are duplicates, it will replaced!
     * @return a list of auto-generated ids from Room database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllWeatherStatusEntities(weatherStatusEntities: List<WeatherStatusEntity>): List<Long>

    /**
     * Update given [CurrentWeatherEntity] model into the database.
     * NOTE: if operation failed for any reason, nothing will change!
     */
    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateCurrentWeatherEntity(currentWeatherEntity: CurrentWeatherEntity)

    /**
     * Search into database for given [latitude] and [longitude].
     * @return [CurrentWeatherEntity] if it is exist, null otherwise.
     */
    @Query("SELECT * FROM tbl_current_weather WHERE latitude =:latitude AND longitude =:longitude")
    suspend fun getCurrentWeatherEntityByCoordinates(
        latitude: Double,
        longitude: Double
    ): CurrentWeatherEntity?

    /**
     * Search and return list of [WeatherStatusEntity] models for given [CurrentWeatherEntity] id.
     * @return list of [WeatherStatusEntity] models if there is any, empty list otherwise.
     */
    @Query("SELECT * FROM tbl_weather_status WHERE current_weather_id =:currentWeatherId")
    suspend fun getWeatherStatusesByCurrentWeatherId(currentWeatherId: Long): List<WeatherStatusEntity>

    /**
     * Delete all [WeatherStatusEntity] models of given [CurrentWeatherEntity] id.
     */
    @Query("DELETE FROM tbl_weather_status WHERE current_weather_id =:currentWeatherId")
    suspend fun deleteWeatherStatuesByCurrentWeatherId(currentWeatherId: Long)

    /**
     * Delete all [CurrentWeatherEntity] and [WeatherStatusEntity] models which they are older than [selectedTimeStamp].
     */
    @Query("DELETE FROM tbl_current_weather WHERE updated_at<=:selectedTimeStamp")
    suspend fun deleteCurrentWeatherEntitiesOlderThan(selectedTimeStamp: Long)

    /**
     * Delete everything exist on both [CurrentWeatherEntity] and [WeatherStatusEntity] tables.
     */
    @Query("DELETE FROM tbl_current_weather")
    suspend fun invalidateCache()
}
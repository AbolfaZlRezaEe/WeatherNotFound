package me.learning.weathernotfound.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.databaseModels.FiveDayThreeHourForecastEntity
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.databaseModels.ForecastEntity
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.databaseModels.WeatherInformationEntity

/**
 * Provide everything you need to access and operate on FiveDayThreeHourForecast information
 */
@Dao
internal interface FiveDayThreeHourDao {

    /**
     * Insert a [FiveDayThreeHourForecastEntity] model into the database.
     * NOTE: if the given [FiveDayThreeHourForecastEntity] is duplicate, it will replaced!
     * @return auto-generated id from Room database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFiveDayThreeHourForecastEntity(
        fiveDayThreeHourForecastEntity: FiveDayThreeHourForecastEntity
    ): Long

    /**
     * Insert a list of [ForecastEntity] models into the database.
     * NOTE: if the given [ForecastEntity] is duplicate, it will replaced!
     * @return a list of auto-generated ids from Room database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfForecastEntities(
        forecastEntities: List<ForecastEntity>
    ): List<Long>

    /**
     * Insert a list of [WeatherInformationEntity] models into the database.
     * NOTE: if the given [WeatherInformationEntity] is duplicate, it will replaced!
     * @return a list of auto-generated ids from Room database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfWeatherInformationEntities(
        weatherInformationEntities: List<WeatherInformationEntity>
    ): List<Long>

    /**
     * Update given [FiveDayThreeHourForecastEntity] model into the database.
     * NOTE: if operation failed for any reason, nothing will change!
     */
    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateFiveDayThreeHourForecastEntity(
        fiveDayThreeHourForecastEntity: FiveDayThreeHourForecastEntity
    )

    /**
     * Delete all [ForecastEntity] and [WeatherInformationEntity] models of given [FiveDayThreeHourForecastEntity] id.
     */
    @Query("DELETE FROM tbl_forecast WHERE five_day_forecast_id=:fiveDayForecastId")
    suspend fun deleteForecastEntitiesByFiveDayForecastId(fiveDayForecastId: Long)

    /**
     * Search for [FiveDayThreeHourForecastEntity] model which is match with given coordinates.
     * @return a [FiveDayThreeHourForecastEntity] model if exist, null otherwise.
     */
    @Query("SELECT * FROM tbl_five_three_forecast WHERE city_latitude=:latitude AND city_longitude=:longitude")
    suspend fun getFiveThreeHourForecastEntityByCoordinates(
        latitude: Double,
        longitude: Double
    ): FiveDayThreeHourForecastEntity?

    /**
     * Search for all [ForecastEntity] models which are belong to the given [fiveDayForecastId].
     * @return list of [ForecastEntity] models if exist, null otherwise.
     */
    @Query("SELECT * FROM tbl_forecast WHERE five_day_forecast_id=:fiveDayForecastId")
    suspend fun getForecastEntitiesListByFiveDayForecastId(fiveDayForecastId: Long): List<ForecastEntity>

    /**
     * Search for all [WeatherInformationEntity] models which are belong to the given [forecastId].
     * @return list of [WeatherInformationEntity] models if exist, null otherwise.
     */
    @Query("SELECT * FROM tbl_weather_information WHERE forecast_id=:forecastId")
    suspend fun getWeatherInformationEntitiesByForecastId(forecastId: Long): List<WeatherInformationEntity>

    /**
     * Delete all [FiveDayThreeHourForecastEntity], [ForecastEntity] and [WeatherInformationEntity]
     * models which they are older than [selectedTimeStamp].
     */
    @Query("DELETE FROM tbl_five_three_forecast WHERE updated_at<=:selectedTimeStamp")
    suspend fun deleteFiveDayThreeHourEntitiesOlderThan(selectedTimeStamp: Long)

    /**
     * Delete everything exist on [FiveDayThreeHourForecastEntity], [ForecastEntity] and [WeatherInformationEntity] tables.
     */
    @Query("DELETE FROM tbl_five_three_forecast")
    suspend fun invalidateCache()
}
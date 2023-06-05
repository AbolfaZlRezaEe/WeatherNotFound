package me.learning.weathernotfound.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.databaseModels.FiveDayThreeHourForecastEntity
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.databaseModels.ForecastEntity
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.databaseModels.WeatherInformationEntity

@Dao
internal interface FiveDayThreeHourDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFiveDayThreeHourForecastEntity(
        fiveDayThreeHourForecastEntity: FiveDayThreeHourForecastEntity
    ): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfForecastEntities(
        forecastEntities: List<ForecastEntity>
    ): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfWeatherInformationEntities(
        weatherInformationEntities: List<WeatherInformationEntity>
    ): List<Long>

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateFiveDayThreeHourForecastEntity(
        fiveDayThreeHourForecastEntity: FiveDayThreeHourForecastEntity
    )

    @Query("DELETE FROM tbl_forecast WHERE five_day_forecast_id=:fiveDayForecastId")
    suspend fun deleteForecastEntitiesByFiveDayForecastId(fiveDayForecastId: Long)

    @Query("SELECT * FROM tbl_five_three_forecast WHERE city_latitude=:latitude AND city_longitude=:longitude")
    suspend fun getFiveThreeHourForecastEntityByCoordinates(
        latitude: Double,
        longitude: Double
    ): FiveDayThreeHourForecastEntity

    @Query("SELECT * FROM tbl_forecast WHERE five_day_forecast_id=:fiveDayForecastId")
    suspend fun getForecastEntitiesListByFiveDayForecastId(fiveDayForecastId: Long): List<ForecastEntity>

    @Query("SELECT * FROM tbl_weather_information WHERE forecast_id=:forecastId")
    suspend fun getWeatherInformationEntitiesByForecastId(forecastId: Long): List<WeatherInformationEntity>

    @Query("DELETE FROM tbl_five_three_forecast WHERE updated_at<=:selectedTimeStamp")
    suspend fun deleteFiveDayThreeHourEntitiesOlderThan(selectedTimeStamp: Long)
}
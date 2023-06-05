package me.learning.weathernotfound.domain.fiveDayThreeHourForecast.databaseModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tbl_weather_information",
    foreignKeys = [ForeignKey(
        entity = ForecastEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("forecast_id"),
        onDelete = ForeignKey.CASCADE,
    )]
)
data class WeatherInformationEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long? = null,
    @ColumnInfo(name = "forecast_id", index = true)
    val forecastId: Long,
    @ColumnInfo(name = "weather_status_id")
    val weatherInformationStatusId: Int,
    @ColumnInfo(name = "status")
    val status: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "icon")
    val icon: String,
)

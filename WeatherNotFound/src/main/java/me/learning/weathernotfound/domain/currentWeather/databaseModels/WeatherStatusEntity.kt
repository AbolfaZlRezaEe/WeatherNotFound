package me.learning.weathernotfound.domain.currentWeather.databaseModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tbl_weather_status",
    foreignKeys = [ForeignKey(
        entity = CurrentWeatherEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("current_weather_id"),
        onDelete = ForeignKey.CASCADE,
    )]
)
internal data class WeatherStatusEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long? = null,
    @ColumnInfo(name = "current_weather_id")
    val currentWeatherId: Long,
    @ColumnInfo(name = "weather_status_id")
    val weatherStatusId: Int,
    @ColumnInfo(name = "status")
    val status: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "icon")
    val icon: String,
)

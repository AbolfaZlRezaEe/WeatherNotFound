package me.learning.weathernotfound.domain.currentWeather.databaseModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "tbl_current_weather")
internal data class CurrentWeatherEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long? = null,
    @ColumnInfo(name = "city_id")
    val cityId: Int,
    @ColumnInfo(name = "city_name")
    val cityName: String,
    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo(name = "longitude")
    val longitude: Double,
    @ColumnInfo(name = "base")
    val base: String,
    @ColumnInfo(name = "cloudiness")
    val cloudiness: Int?,
    @ColumnInfo(name = "feels_like")
    val feelsLike: Double,
    @ColumnInfo(name = "ground_level_pressure")
    val groundLevelPressure: Int,
    @ColumnInfo(name = "humidity")
    val humidity: Int,
    @ColumnInfo(name = "pressure")
    val pressure: Int,
    @ColumnInfo(name = "sea_level_pressure")
    val seaLevelPressure: Int,
    @ColumnInfo(name = "temperature")
    val temperature: Double,
    @ColumnInfo(name = "max_temperature")
    val maximumTemperature: Double,
    @ColumnInfo(name = "min_temperature")
    val minimumTemperature: Double,
    @ColumnInfo(name = "degree")
    val degree: Int?,
    @ColumnInfo(name = "gust")
    val gust: Double?,
    @ColumnInfo(name = "speed")
    val speed: Double?,
    @ColumnInfo(name = "one_hour_rain_information")
    val oneHourRainInformation: Double?,
    @ColumnInfo(name = "three_hour_rain_information")
    val threeHourRainInformation: Double?,
    @ColumnInfo(name = "one_hour_snow_information")
    val oneHourSnowInformation: Double?,
    @ColumnInfo(name = "three_hour_snow_information")
    val threeHourSnowInformation: Double?,
    @ColumnInfo(name = "sys_id")
    val sysId: Int,
    @ColumnInfo(name = "country_name")
    val countryName: String?,
    @ColumnInfo(name = "sunrise")
    val sunrise: Int,
    @ColumnInfo(name = "sunset")
    val sunset: Int,
    @ColumnInfo(name = "type")
    val type: Int,
    @ColumnInfo(name = "visibility")
    val visibility: Int,
    @ColumnInfo(name = "datetime")
    val dateTime: Int,
    @ColumnInfo(name = "timezone")
    val timezone: Int,
    @ColumnInfo(name = "created_at")
    val createdAt: Date,
    @ColumnInfo(name = "updated_at")
    val updatedAt: Date
)

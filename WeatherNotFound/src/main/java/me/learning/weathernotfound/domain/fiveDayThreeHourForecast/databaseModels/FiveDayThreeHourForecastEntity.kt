package me.learning.weathernotfound.domain.fiveDayThreeHourForecast.databaseModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_five_three_forecast")
internal data class FiveDayThreeHourForecastEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long? = null,
    @ColumnInfo(name = "city_id")
    val cityId: Int,
    @ColumnInfo(name = "city_name")
    val cityName: String,
    @ColumnInfo(name = "city_latitude")
    val latitude: Double,
    @ColumnInfo(name = "city_longitude")
    val longitude: Double,
    @ColumnInfo(name = "country_code")
    val country: String,
    @ColumnInfo(name = "population")
    val population: Int,
    @ColumnInfo(name = "sunrise")
    val sunrise: Int,
    @ColumnInfo(name = "sunset")
    val sunset: Int,
    @ColumnInfo(name = "timezone")
    val timezone: Int
)
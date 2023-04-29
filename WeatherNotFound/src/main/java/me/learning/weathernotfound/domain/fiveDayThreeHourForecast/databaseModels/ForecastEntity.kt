package me.learning.weathernotfound.domain.fiveDayThreeHourForecast.databaseModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tbl_fivethree_forecast",
    foreignKeys = [ForeignKey(
        entity = FiveDayThreeHourForecastEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("forecast_id"),
        onDelete = ForeignKey.CASCADE,
    )]
)
internal data class ForecastEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long? = null,
    @ColumnInfo(name = "forecast_id")
    val forecastId: Long,
    @ColumnInfo(name = "time_of_data")
    val timeOfData: Int,
    @ColumnInfo(name = "temperature")
    val temperature: Double,
    @ColumnInfo(name = "feels_like")
    val feelsLike: Double,
    @ColumnInfo(name = "minimum_temp")
    val minimumTemperature: Double,
    @ColumnInfo(name = "maximum_temp")
    val maximumTemperature: Double,
    @ColumnInfo(name = "pressure")
    val pressure: Int,
    @ColumnInfo(name = "sea_level_pressure")
    val seaLevelPressure: Int,
    @ColumnInfo(name = "ground_level_pressure")
    val groundLevelPressure: Int,
    @ColumnInfo(name = "humidity")
    val humidity: Int,
    @ColumnInfo(name = "temp_kf")
    val tempKf: Double,
    @ColumnInfo(name = "cloudiness")
    val cloudiness: Int,
    @ColumnInfo(name = "speed")
    val speed: Double,
    @ColumnInfo(name = "degree")
    val degree: Int,
    @ColumnInfo(name = "gust")
    val gust: Double,
    @ColumnInfo(name = "three_hour_rain_information")
    val threeHourRainInformation: Double?,
    @ColumnInfo(name = "three_hour_snow_information")
    val threeHourSnowInformation: Double,
    @ColumnInfo(name = "day_status")
    val dayStatus: String,
    @ColumnInfo(name = "visibility")
    val visibility: Int,
    @ColumnInfo(name = "probability_of_precipitation")
    val probabilityOfPrecipitation: Double,
    @ColumnInfo(name = "time_of_data_readable_format")
    val timeOfDataInReadableFormat: String
) 
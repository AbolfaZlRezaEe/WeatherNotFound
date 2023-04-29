package me.learning.weathernotfound.domain.fiveDayThreeHourForecast.networkModels

import com.google.gson.annotations.SerializedName

/**
 * Represents the weather condition.
 * @property temperature
 * @property feelsLike The human temperature perception of weather
 * @property maximumTemperature The maximum temperature of given coordinates
 * @property minimumTemperature The minimum temperature of given coordinates
 * @property pressure
 * @property seaLevel Atmospheric pressure on the sea level
 * @property groundLevelPressure Atmospheric pressure on the ground level
 * @property humidity
 * @property tempKf Internal parameter represented by OpenWeatherMap
 */
data class WeatherConditionInformationResponseModel(
    @SerializedName("temp")
    val temperature: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("temp_min")
    val minimumTemperature: Double,
    @SerializedName("temp_max")
    val maximumTemperature: Double,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("sea_level")
    val seaLevel: Int,
    @SerializedName("grnd_level")
    val groundLevelPressure: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("temp_kf")
    val tempKf: Double
)
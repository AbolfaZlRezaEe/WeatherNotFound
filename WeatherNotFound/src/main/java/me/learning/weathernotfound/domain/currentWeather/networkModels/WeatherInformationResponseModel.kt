package me.learning.weathernotfound.domain.currentWeather.networkModels

import com.google.gson.annotations.SerializedName

/**
 * Representing weather information of given coordinates
 * @property feelsLike The human temperature perception of weather
 * @property groundLevelPressure Atmospheric pressure on the ground level
 * @property humidity
 * @property pressure
 * @property seaLevelPressure Atmospheric pressure on the sea level
 * @property temperature
 * @property maximumTemperature The maximum temperature of given coordinates
 * @property minimumTemperature The minimum temperature of given coordinates
 */
internal data class WeatherInformationResponseModel(
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("grnd_level")
    val groundLevelPressure: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("sea_level")
    val seaLevelPressure: Int,
    @SerializedName("temp")
    val temperature: Double,
    @SerializedName("temp_max")
    val maximumTemperature: Double,
    @SerializedName("temp_min")
    val minimumTemperature: Double
)
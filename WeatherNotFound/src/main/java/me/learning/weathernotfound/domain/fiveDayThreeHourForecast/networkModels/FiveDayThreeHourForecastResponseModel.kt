package me.learning.weathernotfound.domain.fiveDayThreeHourForecast.networkModels

import com.google.gson.annotations.SerializedName

/**
 * 5 Day 3 hour forecast for given coordinates
 * @property httpResponseCode Represents the http response code of request
 * @property httpResponseMessage Represents the http response message of request
 * @property cnt Represents the number of timestamps for the given coordinates
 * @property cityInformation Represents the city information of given coordinates
 * @property forecastResponseModels Represents the list of forecasts for given coordinates
 * @see <a href="https://openweathermap.org/forecast5">OpenWeatherMap api documentation</a>
 */
internal data class FiveDayThreeHourForecastResponseModel(
    @SerializedName("cod")
    val httpResponseCode: String,
    @SerializedName("message")
    val httpResponseMessage: Int,
    @SerializedName("cnt")
    val cnt: Int,
    @SerializedName("city")
    val cityInformation: CityInformationResponseModel,
    @SerializedName("list")
    val forecastResponseModels: List<ForecastResponseModel>
)
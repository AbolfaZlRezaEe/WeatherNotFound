package me.learning.weathernotfound.domain.fiveDayThreeHourForecast.networkModels

import com.google.gson.annotations.SerializedName

/**
 * Represent forecast of each 3 hours
 * @property timeOfData The Time of data forecasted, unix, UTC. for example: (1682845200)
 * @property weatherConditionInformation Represent the information of temperature, humidity and etc..
 * @property weatherInformation
 * @property cloudsInformation
 * @property windInformation
 * @property rainInformation
 * @property snowInformation
 * @property dayInformation
 * @property visibility The average visibility. maximum value is 10km
 * @property probabilityOfPrecipitation
 * @property timeOfDataInReadableFormat Time of data forecasted, ISO, UTC. for example: (2023-04-30 09:00:00)
 */
internal data class ForecastResponseModel(
    @SerializedName("dt")
    val timeOfData: Int,
    @SerializedName("main")
    val weatherConditionInformation: WeatherConditionInformationResponseModel,
    @SerializedName("weather")
    val weatherInformation: List<WeatherInformationResponseModel>,
    @SerializedName("clouds")
    val cloudsInformation: CloudsInformationResponseModel,
    @SerializedName("wind")
    val windInformation: WindInformationResponseModel,
    @SerializedName("rain")
    val rainInformation: RainInformationResponseModel,
    @SerializedName("snow")
    val snowInformation: SnowInformationResponseModel,
    @SerializedName("sys")
    val dayInformation: DayInformationResponseModel,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("pop")
    val probabilityOfPrecipitation: Double,
    @SerializedName("dt_txt")
    val timeOfDataInReadableFormat: String
)
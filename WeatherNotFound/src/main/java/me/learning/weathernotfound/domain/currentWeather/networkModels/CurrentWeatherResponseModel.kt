package me.learning.weathernotfound.domain.currentWeather.networkModels

import com.google.gson.annotations.SerializedName

/**
 * Current weather information for given coordinates
 * @property cityId The id of city represented by OpenWeatherMap
 * @property cityName The city name of given coordinates
 * @property cityCoordinates The city coordinate of given coordinates
 * @property weatherStatus The general status of given coordinates
 * @property base Internal parameter represented by OpenWeatherMap
 * @property cloudInformationInformation The status of clouds of given coordinates
 * @property weatherInformation The weather information of given coordinates. like temperature, humidity, etc...
 * @property windInformation The wind information of given coordinates. like degree, gust, speed, etc...
 * @property rainInformation The rain information of given coordinates
 * NOTE: This information is highly depends on the city and may not available!
 * @property snowInformation The snow information of given coordinates
 * NOTE: This information is highly depends on the city and may not available!
 * @property cityInformation The city information of given coordinates. like country name, sunset, etc...
 * @property visibility The value of the visibility of given coordinates. Max: 10km
 * @property dateTime Time of data calculation, unix, UTC
 * @property timezone Shift in seconds from UTC
 * @property httpResponseCode Internal parameter represented by OpenWeatherMap
 * @see <a href="https://openweathermap.org/current#current_JSON">OpenWeatherMap api documentation</a>
 */
internal data class CurrentWeatherResponseModel(
    @SerializedName("id")
    val cityId: Int,
    @SerializedName("name")
    val cityName: String,
    @SerializedName("coord")
    val cityCoordinates: CityCoordinatesResponseModel,
    @SerializedName("weather")
    val weatherStatus: List<WeatherStatusResponseModel>,
    @SerializedName("base")
    val base: String,
    @SerializedName("clouds")
    val cloudInformationInformation: CloudInformationResponseModel?,
    @SerializedName("main")
    val weatherInformation: WeatherInformationResponseModel,
    @SerializedName("wind")
    val windInformation: WindInformationResponseModel?,
    @SerializedName("rain")
    val rainInformation: RainInformationResponseModel?,
    @SerializedName("snow")
    val snowInformation: SnowInformationResponseModel?,
    @SerializedName("sys")
    val cityInformation: CityInformationResponseModel,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("dt")
    val dateTime: Int,
    @SerializedName("timezone")
    val timezone: Int,
    @SerializedName("cod")
    val httpResponseCode: Int,
)
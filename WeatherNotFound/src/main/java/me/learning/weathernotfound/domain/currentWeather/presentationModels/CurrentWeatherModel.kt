package me.learning.weathernotfound.domain.currentWeather.presentationModels

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
 * @see <a href="https://openweathermap.org/current#current_JSON">OpenWeatherMap api documentation</a>
 */
data class CurrentWeatherModel(
    val cityId: Int,
    val cityName: String,
    val cityCoordinates: CityCoordinatesModel,
    val weatherStatus: List<WeatherStatusModel>,
    val base: String,
    val cloudInformationInformation: CloudInformationModel,
    val weatherInformation: WeatherInformationModel,
    val windInformation: WindInformationModel,
    val rainInformation: RainInformationModel,
    val snowInformation: SnowInformationModel,
    val cityInformation: CityInformationModel,
    val visibility: Int,
    val dateTime: Int,
    val timezone: Int,
)

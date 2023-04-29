package me.learning.weathernotfound.domain.fiveDayThreeHourForecast.presentationModels


/**
 * 5 Day 3 hour forecast for given coordinates
 * @property cnt Represents the number of timestamps for the given coordinates
 * @property cityInformation Represents the city information of given coordinates
 * @property forecastModels Represents the list of forecasts for given coordinates
 * @see <a href="https://openweathermap.org/forecast5">OpenWeatherMap api documentation</a>
 */
data class FiveDayThreeHourForecastModel(
    val cnt: Int,
    val cityInformation: CityInformationModel,
    val forecastModels: List<ForecastModel>
)
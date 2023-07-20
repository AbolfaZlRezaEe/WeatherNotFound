package me.learning.weathernotfound.domain.fiveDayThreeHourForecast.presentationModels

/**
 * Represents forecast of each 3 hours
 * @property timeOfData The Time of data forecasted, unix, UTC. for example: (1682845200)
 * @property weatherConditionInformation Represents the information of temperature, humidity and etc..
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
data class ForecastModel(
    val timeOfData: Int,
    val weatherConditionInformation: WeatherConditionInformationModel,
    val weatherInformation: List<WeatherInformationModel>,
    val cloudsInformation: CloudsInformationModel,
    val windInformation: WindInformationModel,
    val rainInformation: RainInformationModel,
    val snowInformation: SnowInformationModel,
    val dayInformation: DayInformationModel,
    val visibility: Int,
    val probabilityOfPrecipitation: Double,
    val timeOfDataInReadableFormat: String
)
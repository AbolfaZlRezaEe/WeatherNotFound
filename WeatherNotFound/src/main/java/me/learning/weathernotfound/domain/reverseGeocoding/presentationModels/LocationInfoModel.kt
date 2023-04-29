package me.learning.weathernotfound.domain.reverseGeocoding.presentationModels

/**
 * All the coordinate information for given coordinates
 * @property locationName The name of that coordinate in english
 * @property countryName The name of country that coordinate is on
 * @property locationCoordinates The coordinates of given coordinates
 * @property locationNameInOtherLanguages The list of names of this coordinate in different languages
 * @see <a href="https://openweathermap.org/api/geocoding-api#reverse">OpenWeatherMap api documentation</a>
 */
data class LocationInfoModel(
    val locationName: String,
    val countryName: String,
    val locationCoordinates: LocationCoordinatesModel,
    val locationNameInOtherLanguages: LocationNamesModel,
)

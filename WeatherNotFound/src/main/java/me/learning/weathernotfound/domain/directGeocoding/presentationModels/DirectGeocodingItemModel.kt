package me.learning.weathernotfound.domain.directGeocoding.presentationModels

/**
 * The coordinate information for city name
 * @property locationName The name of that coordinate in english
 * @property countryName The name of country that coordinate is on
 * @property locationCoordinates The coordinates of given coordinates
 * @property locationNameInOtherLanguages The list of names of this coordinate in different languages
 * @see <a href="https://openweathermap.org/api/geocoding-api#direct">OpenWeatherMap api documentation</a>
 */
data class DirectGeocodingItemModel(
    val locationName: String,
    val countryName: String,
    val locationCoordinates: LocationCoordinatesModel,
    val locationNameInOtherLanguages: LocationNamesModel,
)
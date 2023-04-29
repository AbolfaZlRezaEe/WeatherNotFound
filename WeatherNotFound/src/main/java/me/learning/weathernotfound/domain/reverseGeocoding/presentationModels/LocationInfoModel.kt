package me.learning.weathernotfound.domain.reverseGeocoding.presentationModels

data class LocationInfoModel(
    val locationName: String,
    val countryName: String,
    val locationCoordinates: LocationCoordinatesModel,
    val locationNameInOtherLanguages: LocationNamesModel,
)

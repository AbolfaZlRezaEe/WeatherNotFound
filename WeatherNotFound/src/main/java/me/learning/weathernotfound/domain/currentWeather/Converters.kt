package me.learning.weathernotfound.domain.currentWeather

import me.learning.weathernotfound.domain.currentWeather.databaseModels.CurrentWeatherEntity
import me.learning.weathernotfound.domain.currentWeather.databaseModels.WeatherStatusEntity
import me.learning.weathernotfound.domain.currentWeather.networkModels.CurrentWeatherResponseModel
import me.learning.weathernotfound.domain.currentWeather.networkModels.WeatherStatusResponseModel
import me.learning.weathernotfound.domain.currentWeather.presentationModels.CityCoordinatesModel
import me.learning.weathernotfound.domain.currentWeather.presentationModels.CityInformationModel
import me.learning.weathernotfound.domain.currentWeather.presentationModels.CloudInformationModel
import me.learning.weathernotfound.domain.currentWeather.presentationModels.CurrentWeatherModel
import me.learning.weathernotfound.domain.currentWeather.presentationModels.RainInformationModel
import me.learning.weathernotfound.domain.currentWeather.presentationModels.SnowInformationModel
import me.learning.weathernotfound.domain.currentWeather.presentationModels.WeatherInformationModel
import me.learning.weathernotfound.domain.currentWeather.presentationModels.WeatherStatusModel
import me.learning.weathernotfound.domain.currentWeather.presentationModels.WindInformationModel
import me.learning.weathernotfound.utils.Utilities

internal object Converters {

    // Network to Database entity converters
    fun currentWeatherResponseToCurrentWeatherEntity(
        response: CurrentWeatherResponseModel,
        entityId: Long? = null,
    ): CurrentWeatherEntity {
        return CurrentWeatherEntity(
            id = entityId,
            cityId = response.cityId,
            cityName = response.cityName,
            latitude = response.cityCoordinates.latitude,
            longitude = response.cityCoordinates.longitude,
            base = response.base,
            cloudiness = response.cloudInformationInformation?.cloudiness,
            feelsLike = response.weatherInformation.feelsLike,
            groundLevelPressure = response.weatherInformation.groundLevelPressure,
            humidity = response.weatherInformation.humidity,
            pressure = response.weatherInformation.pressure,
            seaLevelPressure = response.weatherInformation.seaLevelPressure,
            temperature = response.weatherInformation.temperature,
            maximumTemperature = response.weatherInformation.maximumTemperature,
            minimumTemperature = response.weatherInformation.minimumTemperature,
            degree = response.windInformation?.degree,
            gust = response.windInformation?.gust,
            speed = response.windInformation?.speed,
            oneHourRainInformation = response.rainInformation?.oneHour,
            threeHourRainInformation = response.rainInformation?.threeHour,
            oneHourSnowInformation = response.snowInformation?.oneHour,
            threeHourSnowInformation = response.snowInformation?.threeHour,
            sysId = response.cityInformation.id,
            countryName = response.cityInformation.countryName,
            sunrise = response.cityInformation.sunrise,
            sunset = response.cityInformation.sunset,
            type = response.cityInformation.type,
            visibility = response.visibility,
            dateTime = response.dateTime,
            timezone = response.timezone,
            createdAt = Utilities.getCurrentDateTime(),
            updatedAt = Utilities.getCurrentDateTime(),
        )
    }

    fun weatherStatusResponseToWeatherStatusEntity(
        weatherStatusResponseModels: List<WeatherStatusResponseModel>,
        currentWeatherId: Long
    ): List<WeatherStatusEntity> {
        return weatherStatusResponseModels.map {
            WeatherStatusEntity(
                id = null,
                currentWeatherId = currentWeatherId,
                weatherStatusId = it.id,
                status = it.status,
                description = it.description,
                icon = it.icon,
            )
        }
    }

    // Database to Presentation model converters
    fun currentWeatherEntityToCurrentWeatherModel(
        currentWeather: CurrentWeatherEntity,
        weatherStatuses: List<WeatherStatusEntity>
    ): CurrentWeatherModel {
        return CurrentWeatherModel(
            cityId = currentWeather.cityId,
            cityName = currentWeather.cityName,
            cityCoordinates = CityCoordinatesModel(
                latitude = currentWeather.latitude,
                longitude = currentWeather.longitude
            ),
            base = currentWeather.base,
            cloudInformationInformation = CloudInformationModel(
                cloudiness = currentWeather.cloudiness
            ),
            weatherInformation = WeatherInformationModel(
                feelsLike = currentWeather.feelsLike,
                groundLevelPressure = currentWeather.groundLevelPressure,
                humidity = currentWeather.humidity,
                pressure = currentWeather.pressure,
                seaLevelPressure = currentWeather.seaLevelPressure,
                temperature = currentWeather.temperature,
                maximumTemperature = currentWeather.maximumTemperature,
                minimumTemperature = currentWeather.minimumTemperature,
            ),
            windInformation = WindInformationModel(
                degree = currentWeather.degree,
                gust = currentWeather.gust,
                speed = currentWeather.speed
            ),
            rainInformation = RainInformationModel(
                oneHour = currentWeather.oneHourRainInformation,
                threeHour = currentWeather.threeHourRainInformation
            ),
            snowInformation = SnowInformationModel(
                oneHour = currentWeather.oneHourSnowInformation,
                threeHour = currentWeather.threeHourSnowInformation
            ),
            cityInformation = CityInformationModel(
                id = currentWeather.sysId,
                countryName = currentWeather.countryName,
                sunrise = currentWeather.sunrise,
                sunset = currentWeather.sunset,
                type = currentWeather.type
            ),
            visibility = currentWeather.visibility,
            dateTime = currentWeather.dateTime,
            timezone = currentWeather.timezone,
            weatherStatus = weatherStatuses.map { weatherStatusEntityToWeatherStatusModel(it) }
        )
    }

    private fun weatherStatusEntityToWeatherStatusModel(status: WeatherStatusEntity): WeatherStatusModel {
        return WeatherStatusModel(
            id = status.weatherStatusId,
            status = status.status,
            description = status.description,
            icon = status.icon
        )
    }

    // Network to Presentation model converters
    fun currentWeatherResponseModelToCurrentWeatherModel(
        response: CurrentWeatherResponseModel
    ): CurrentWeatherModel {
        return CurrentWeatherModel(
            cityId = response.cityId,
            cityName = response.cityName,
            cityCoordinates = CityCoordinatesModel(
                latitude = response.cityCoordinates.latitude,
                longitude = response.cityCoordinates.longitude,
            ),
            weatherInformation = WeatherInformationModel(
                feelsLike = response.weatherInformation.feelsLike,
                groundLevelPressure = response.weatherInformation.groundLevelPressure,
                humidity = response.weatherInformation.humidity,
                pressure = response.weatherInformation.pressure,
                seaLevelPressure = response.weatherInformation.seaLevelPressure,
                temperature = response.weatherInformation.temperature,
                maximumTemperature = response.weatherInformation.maximumTemperature,
                minimumTemperature = response.weatherInformation.minimumTemperature
            ),
            base = response.base,
            cloudInformationInformation = CloudInformationModel(
                cloudiness = response.cloudInformationInformation?.cloudiness,
            ),
            windInformation = WindInformationModel(
                degree = response.windInformation?.degree,
                gust = response.windInformation?.gust,
                speed = response.windInformation?.speed,
            ),
            rainInformation = RainInformationModel(
                oneHour = response.rainInformation?.oneHour,
                threeHour = response.rainInformation?.threeHour,
            ),
            snowInformation = SnowInformationModel(
                oneHour = response.snowInformation?.oneHour,
                threeHour = response.snowInformation?.threeHour,
            ),
            cityInformation = CityInformationModel(
                id = response.cityInformation.id,
                countryName = response.cityInformation.countryName,
                sunrise = response.cityInformation.sunrise,
                sunset = response.cityInformation.sunset,
                type = response.cityInformation.type
            ),
            visibility = response.visibility,
            dateTime = response.dateTime,
            timezone = response.timezone,
            weatherStatus = response.weatherStatus.map {
                weatherStatusResponseModelToWeatherStatusModel(it)
            }
        )
    }

    private fun weatherStatusResponseModelToWeatherStatusModel(
        response: WeatherStatusResponseModel
    ): WeatherStatusModel {
        return WeatherStatusModel(
            id = response.id,
            status = response.status,
            description = response.description,
            icon = response.icon
        )
    }
}
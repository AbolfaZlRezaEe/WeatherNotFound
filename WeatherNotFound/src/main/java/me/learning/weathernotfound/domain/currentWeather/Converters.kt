package me.learning.weathernotfound.domain.currentWeather

import me.learning.weathernotfound.BuildConfig
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
        val result = CurrentWeatherEntity(
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
        validateNetworkResponseToCurrentWeatherEntityModelConversion(
            responseModel = response,
            entityModel = result
        )
        return result
    }

    fun weatherStatusResponseToWeatherStatusEntity(
        weatherStatusResponseModels: List<WeatherStatusResponseModel>,
        currentWeatherId: Long
    ): List<WeatherStatusEntity> {
        val result = weatherStatusResponseModels.map {
            WeatherStatusEntity(
                id = null,
                currentWeatherId = currentWeatherId,
                weatherStatusId = it.id,
                status = it.status,
                description = it.description,
                icon = it.icon,
            )
        }
        validateNetworkResponseToWeatherStatusEntityModelConversion(
            responseModels = weatherStatusResponseModels,
            entityModels = result
        )
        return result
    }

    // Database to Presentation model converters
    fun currentWeatherEntityToCurrentWeatherModel(
        currentWeather: CurrentWeatherEntity,
        weatherStatuses: List<WeatherStatusEntity>
    ): CurrentWeatherModel {
        val result = CurrentWeatherModel(
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
        validateEntityModelToPresentationModelConversion(
            currentWeatherEntityModel = currentWeather,
            weatherStatusEntityModels = weatherStatuses,
            presentationModel = result
        )
        return result
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
        val result = CurrentWeatherModel(
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
        validateNetworkResponseToPresentationModelConversion(
            responseModel = response,
            presentationModel = result
        )
        return result
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

    // Todo: tests should be in the test section! for now, we just check like this:
    private fun validateNetworkResponseToPresentationModelConversion(
        responseModel: CurrentWeatherResponseModel,
        presentationModel: CurrentWeatherModel,
    ) {
        if (!BuildConfig.DEBUG) return
        assert(responseModel.cityId == presentationModel.cityId)
        assert(responseModel.cityName == presentationModel.cityName)
        assert(responseModel.cityCoordinates.latitude == presentationModel.cityCoordinates.latitude)
        assert(responseModel.cityCoordinates.longitude == presentationModel.cityCoordinates.longitude)
        assert(responseModel.base == presentationModel.base)
        if (responseModel.cloudInformationInformation == null) {
            assert(presentationModel.cloudInformationInformation.cloudiness == null)
        } else {
            assert(responseModel.cloudInformationInformation.cloudiness == presentationModel.cloudInformationInformation.cloudiness)
        }
        assert(responseModel.weatherInformation.feelsLike == presentationModel.weatherInformation.feelsLike)
        assert(responseModel.weatherInformation.groundLevelPressure == presentationModel.weatherInformation.groundLevelPressure)
        assert(responseModel.weatherInformation.humidity == presentationModel.weatherInformation.humidity)
        assert(responseModel.weatherInformation.pressure == presentationModel.weatherInformation.pressure)
        assert(responseModel.weatherInformation.seaLevelPressure == presentationModel.weatherInformation.seaLevelPressure)
        assert(responseModel.weatherInformation.temperature == presentationModel.weatherInformation.temperature)
        assert(responseModel.weatherInformation.maximumTemperature == presentationModel.weatherInformation.maximumTemperature)
        assert(responseModel.weatherInformation.minimumTemperature == presentationModel.weatherInformation.minimumTemperature)
        if (responseModel.windInformation == null) {
            assert(presentationModel.windInformation.degree == null)
            assert(presentationModel.windInformation.gust == null)
            assert(presentationModel.windInformation.speed == null)
        } else {
            assert(responseModel.windInformation.degree == presentationModel.windInformation.degree)
            assert(responseModel.windInformation.gust == presentationModel.windInformation.gust)
            assert(responseModel.windInformation.speed == presentationModel.windInformation.speed)
        }
        if (responseModel.rainInformation == null) {
            assert(presentationModel.rainInformation.oneHour == null)
            assert(presentationModel.rainInformation.threeHour == null)
        } else {
            assert(responseModel.rainInformation.oneHour == presentationModel.rainInformation.oneHour)
            assert(responseModel.rainInformation.threeHour == presentationModel.rainInformation.threeHour)
        }
        if (responseModel.snowInformation == null) {
            assert(presentationModel.snowInformation.oneHour == null)
            assert(presentationModel.snowInformation.threeHour == null)
        } else {
            assert(responseModel.snowInformation.oneHour == presentationModel.snowInformation.oneHour)
            assert(responseModel.snowInformation.threeHour == presentationModel.snowInformation.threeHour)
        }
        assert(responseModel.cityInformation.id == presentationModel.cityInformation.id)
        assert(responseModel.cityInformation.countryName == presentationModel.cityInformation.countryName)
        assert(responseModel.cityInformation.sunrise == presentationModel.cityInformation.sunrise)
        assert(responseModel.cityInformation.sunset == presentationModel.cityInformation.sunset)
        assert(responseModel.cityInformation.type == presentationModel.cityInformation.type)
        assert(responseModel.visibility == presentationModel.visibility)
        assert(responseModel.dateTime == presentationModel.dateTime)
        assert(responseModel.timezone == presentationModel.timezone)

        responseModel.weatherStatus.forEachIndexed { index, item ->
            assert(item.id == presentationModel.weatherStatus[index].id)
            assert(item.status == presentationModel.weatherStatus[index].status)
            assert(item.description == presentationModel.weatherStatus[index].description)
            assert(item.icon == presentationModel.weatherStatus[index].icon)
        }
    }

    private fun validateNetworkResponseToCurrentWeatherEntityModelConversion(
        responseModel: CurrentWeatherResponseModel,
        entityModel: CurrentWeatherEntity
    ) {
        if (!BuildConfig.DEBUG) return
        assert(responseModel.cityId == entityModel.cityId)
        assert(responseModel.cityName == entityModel.cityName)
        assert(responseModel.cityCoordinates.latitude == entityModel.latitude)
        assert(responseModel.cityCoordinates.longitude == entityModel.longitude)
        assert(responseModel.base == entityModel.base)
        if (responseModel.cloudInformationInformation == null) {
            assert(entityModel.cloudiness == null)
        } else {
            assert(responseModel.cloudInformationInformation.cloudiness == entityModel.cloudiness)
        }
        assert(responseModel.weatherInformation.feelsLike == entityModel.feelsLike)
        assert(responseModel.weatherInformation.groundLevelPressure == entityModel.groundLevelPressure)
        assert(responseModel.weatherInformation.humidity == entityModel.humidity)
        assert(responseModel.weatherInformation.pressure == entityModel.pressure)
        assert(responseModel.weatherInformation.seaLevelPressure == entityModel.seaLevelPressure)
        assert(responseModel.weatherInformation.temperature == entityModel.temperature)
        assert(responseModel.weatherInformation.maximumTemperature == entityModel.maximumTemperature)
        assert(responseModel.weatherInformation.minimumTemperature == entityModel.minimumTemperature)
        if (responseModel.windInformation == null) {
            assert(entityModel.degree == null)
            assert(entityModel.gust == null)
            assert(entityModel.speed == null)
        } else {
            assert(responseModel.windInformation.degree == entityModel.degree)
            assert(responseModel.windInformation.gust == entityModel.gust)
            assert(responseModel.windInformation.speed == entityModel.speed)
        }
        if (responseModel.rainInformation == null) {
            assert(entityModel.oneHourRainInformation == null)
            assert(entityModel.threeHourRainInformation == null)
        } else {
            assert(responseModel.rainInformation.oneHour == entityModel.oneHourRainInformation)
            assert(responseModel.rainInformation.threeHour == entityModel.threeHourRainInformation)
        }
        if (responseModel.snowInformation == null) {
            assert(entityModel.oneHourSnowInformation == null)
            assert(entityModel.threeHourSnowInformation == null)
        } else {
            assert(responseModel.snowInformation.oneHour == entityModel.oneHourSnowInformation)
            assert(responseModel.snowInformation.threeHour == entityModel.threeHourSnowInformation)
        }
        assert(responseModel.cityInformation.countryName == entityModel.countryName)
        assert(responseModel.cityInformation.sunrise == entityModel.sunrise)
        assert(responseModel.cityInformation.sunset == entityModel.sunset)
        assert(responseModel.cityInformation.type == entityModel.type)
        assert(responseModel.visibility == entityModel.visibility)
        assert(responseModel.dateTime == entityModel.dateTime)
        assert(responseModel.timezone == entityModel.timezone)
    }

    private fun validateNetworkResponseToWeatherStatusEntityModelConversion(
        responseModels: List<WeatherStatusResponseModel>,
        entityModels: List<WeatherStatusEntity>
    ) {
        if (!BuildConfig.DEBUG) return
        responseModels.forEachIndexed { index, responseModel ->
            assert(responseModel.status == entityModels[index].status)
            assert(responseModel.description == entityModels[index].description)
            assert(responseModel.icon == entityModels[index].icon)
        }
    }

    private fun validateEntityModelToPresentationModelConversion(
        currentWeatherEntityModel: CurrentWeatherEntity,
        weatherStatusEntityModels: List<WeatherStatusEntity>,
        presentationModel: CurrentWeatherModel
    ) {
        if (!BuildConfig.DEBUG) return
        assert(presentationModel.cityId == currentWeatherEntityModel.cityId)
        assert(presentationModel.cityName == currentWeatherEntityModel.cityName)
        assert(presentationModel.cityCoordinates.latitude == currentWeatherEntityModel.latitude)
        assert(presentationModel.cityCoordinates.longitude == currentWeatherEntityModel.longitude)
        assert(presentationModel.base == currentWeatherEntityModel.base)
        if (presentationModel.cloudInformationInformation.cloudiness == null) {
            assert(currentWeatherEntityModel.cloudiness == null)
        } else {
            assert(presentationModel.cloudInformationInformation.cloudiness == currentWeatherEntityModel.cloudiness)
        }
        assert(presentationModel.weatherInformation.feelsLike == currentWeatherEntityModel.feelsLike)
        assert(presentationModel.weatherInformation.groundLevelPressure == currentWeatherEntityModel.groundLevelPressure)
        assert(presentationModel.weatherInformation.humidity == currentWeatherEntityModel.humidity)
        assert(presentationModel.weatherInformation.pressure == currentWeatherEntityModel.pressure)
        assert(presentationModel.weatherInformation.seaLevelPressure == currentWeatherEntityModel.seaLevelPressure)
        assert(presentationModel.weatherInformation.temperature == currentWeatherEntityModel.temperature)
        assert(presentationModel.weatherInformation.maximumTemperature == currentWeatherEntityModel.maximumTemperature)
        assert(presentationModel.weatherInformation.minimumTemperature == currentWeatherEntityModel.minimumTemperature)
        if (presentationModel.windInformation.degree == null) {
            assert(currentWeatherEntityModel.degree == null)
        } else {
            assert(presentationModel.windInformation.degree == currentWeatherEntityModel.degree)
        }
        if (presentationModel.windInformation.gust == null) {
            assert(currentWeatherEntityModel.gust == null)
        } else {
            assert(presentationModel.windInformation.gust == currentWeatherEntityModel.gust)
        }
        if (presentationModel.windInformation.speed == null) {
            assert(currentWeatherEntityModel.speed == null)
        } else {
            assert(presentationModel.windInformation.speed == currentWeatherEntityModel.speed)
        }
        if (presentationModel.rainInformation.oneHour == null) {
            assert(currentWeatherEntityModel.oneHourRainInformation == null)
        } else {
            assert(presentationModel.rainInformation.oneHour == currentWeatherEntityModel.oneHourRainInformation)
        }
        if (presentationModel.rainInformation.threeHour == null) {
            assert(currentWeatherEntityModel.threeHourRainInformation == null)
        } else {
            assert(presentationModel.rainInformation.threeHour == currentWeatherEntityModel.threeHourRainInformation)
        }
        if (presentationModel.snowInformation.oneHour == null) {
            assert(currentWeatherEntityModel.oneHourSnowInformation == null)
        } else {
            assert(presentationModel.snowInformation.oneHour == currentWeatherEntityModel.oneHourSnowInformation)
        }
        if (presentationModel.snowInformation.threeHour == null) {
            assert(currentWeatherEntityModel.threeHourSnowInformation == null)
        } else {
            assert(presentationModel.snowInformation.threeHour == currentWeatherEntityModel.threeHourSnowInformation)
        }
        assert(presentationModel.cityInformation.countryName == currentWeatherEntityModel.countryName)
        assert(presentationModel.cityInformation.sunrise == currentWeatherEntityModel.sunrise)
        assert(presentationModel.cityInformation.sunset == currentWeatherEntityModel.sunset)
        assert(presentationModel.cityInformation.type == currentWeatherEntityModel.type)
        assert(presentationModel.visibility == currentWeatherEntityModel.visibility)
        assert(presentationModel.dateTime == currentWeatherEntityModel.dateTime)
        assert(presentationModel.timezone == currentWeatherEntityModel.timezone)

        presentationModel.weatherStatus.forEachIndexed { index, weatherStatusModel ->
            assert(weatherStatusModel.status == weatherStatusEntityModels[index].status)
            assert(weatherStatusModel.description == weatherStatusEntityModels[index].description)
            assert(weatherStatusModel.icon == weatherStatusEntityModels[index].icon)
        }
    }
}
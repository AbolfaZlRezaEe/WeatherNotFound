package me.learning.weathernotfound.domain.fiveDayThreeHourForecast

import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.databaseModels.FiveDayThreeHourForecastEntity
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.databaseModels.ForecastEntity
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.databaseModels.WeatherInformationEntity
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.networkModels.FiveDayThreeHourForecastResponseModel
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.networkModels.ForecastResponseModel
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.networkModels.WeatherInformationResponseModel
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.presentationModels.CityCoordinatesModel
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.presentationModels.CityInformationModel
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.presentationModels.CloudsInformationModel
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.presentationModels.DayInformationModel
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.presentationModels.FiveDayThreeHourForecastModel
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.presentationModels.ForecastModel
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.presentationModels.RainInformationModel
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.presentationModels.SnowInformationModel
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.presentationModels.WeatherConditionInformationModel
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.presentationModels.WeatherInformationModel
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.presentationModels.WindInformationModel
import me.learning.weathernotfound.domain.utils.Utilities

internal object Converters {

    // Network to Database entity converters
    fun fiveDayThreeHourForecastResponseToFiveDayThreeHourForecastEntity(
        response: FiveDayThreeHourForecastResponseModel
    ): FiveDayThreeHourForecastEntity {
        return FiveDayThreeHourForecastEntity(
            id = null,
            cnt = response.cnt,
            cityId = response.cityInformation.id,
            cityName = response.cityInformation.cityName,
            latitude = response.cityInformation.cityCoordinates.latitude,
            longitude = response.cityInformation.cityCoordinates.longitude,
            country = response.cityInformation.country,
            population = response.cityInformation.population,
            sunrise = response.cityInformation.sunrise,
            sunset = response.cityInformation.sunset,
            timezone = response.cityInformation.timezone,
            createdAt = Utilities.getCurrentTime(),
            updatedAt = Utilities.getCurrentTime(),
        )
    }

    fun forecastResponseToForecastEntity(
        responses: List<ForecastResponseModel>,
        forecastId: Long,
    ): List<ForecastEntity> {
        return responses.map {
            ForecastEntity(
                id = null,
                forecastId = forecastId,
                timeOfData = it.timeOfData,
                temperature = it.weatherConditionInformation.temperature,
                feelsLike = it.weatherConditionInformation.feelsLike,
                minimumTemperature = it.weatherConditionInformation.minimumTemperature,
                maximumTemperature = it.weatherConditionInformation.maximumTemperature,
                pressure = it.weatherConditionInformation.pressure,
                seaLevelPressure = it.weatherConditionInformation.seaLevelPressure,
                groundLevelPressure = it.weatherConditionInformation.groundLevelPressure,
                humidity = it.weatherConditionInformation.humidity,
                tempKf = it.weatherConditionInformation.tempKf,
                cloudiness = it.cloudsInformation.cloudiness,
                speed = it.windInformation.speed,
                degree = it.windInformation.degree,
                gust = it.windInformation.gust,
                threeHourRainInformation = it.rainInformation.threeHour,
                threeHourSnowInformation = it.snowInformation.threeHour,
                dayStatus = it.dayInformation.dayStatus,
                visibility = it.visibility,
                probabilityOfPrecipitation = it.probabilityOfPrecipitation,
                timeOfDataInReadableFormat = it.timeOfDataInReadableFormat
            )
        }
    }

    fun weatherInformationResponseToWeatherInformationEntity(
        responses: List<WeatherInformationResponseModel>,
        forecastId: Long
    ): List<WeatherInformationEntity> {
        return responses.map {
            WeatherInformationEntity(
                id = null,
                forecastId = forecastId,
                weatherInformationStatusId = it.id,
                status = it.status,
                description = it.description,
                icon = it.icon
            )
        }
    }

    // Database to Presentation model converters
    fun fiveDayThreeOurForecastEntityToFiveDayThreeHourForecastModel(
        fiveDayEntity: FiveDayThreeHourForecastEntity,
        forecastEntities: List<ForecastEntity>,
        weatherInformationEntity: List<WeatherInformationEntity>
    ): FiveDayThreeHourForecastModel {
        return FiveDayThreeHourForecastModel(
            cnt = fiveDayEntity.cnt,
            cityInformation = CityInformationModel(
                id = fiveDayEntity.cityId,
                cityName = fiveDayEntity.cityName,
                cityCoordinates = CityCoordinatesModel(
                    latitude = fiveDayEntity.latitude,
                    longitude = fiveDayEntity.longitude,
                ),
                country = fiveDayEntity.country,
                population = fiveDayEntity.population,
                sunrise = fiveDayEntity.sunrise,
                sunset = fiveDayEntity.sunset,
                timezone = fiveDayEntity.timezone,
            ),
            forecastModels = forecastEntities.map {
                forecastEntityToForecastModel(
                    entity = it,
                    weatherInformation = weatherInformationEntity
                )
            }
        )
    }

    private fun forecastEntityToForecastModel(
        entity: ForecastEntity,
        weatherInformation: List<WeatherInformationEntity>
    ): ForecastModel {
        return ForecastModel(
            timeOfData = entity.timeOfData,
            weatherConditionInformation = WeatherConditionInformationModel(
                temperature = entity.temperature,
                feelsLike = entity.feelsLike,
                minimumTemperature = entity.minimumTemperature,
                maximumTemperature = entity.maximumTemperature,
                pressure = entity.pressure,
                seaLevelPressure = entity.seaLevelPressure,
                groundLevelPressure = entity.groundLevelPressure,
                humidity = entity.humidity,
                tempKf = entity.tempKf,
            ),
            weatherInformation = weatherInformation.map {
                weatherStatusEntityToWeatherInformationModel(entity = it)
            },
            cloudsInformation = CloudsInformationModel(
                cloudiness = entity.cloudiness,
            ),
            windInformation = WindInformationModel(
                speed = entity.speed,
                degree = entity.degree,
                gust = entity.gust,
            ),
            rainInformation = RainInformationModel(
                threeHour = entity.threeHourRainInformation,
            ),
            snowInformation = SnowInformationModel(
                threeHour = entity.threeHourSnowInformation,
            ),
            dayInformation = DayInformationModel(
                dayStatus = entity.dayStatus,
            ),
            visibility = entity.visibility,
            probabilityOfPrecipitation = entity.probabilityOfPrecipitation,
            timeOfDataInReadableFormat = entity.timeOfDataInReadableFormat,
        )
    }

    private fun weatherStatusEntityToWeatherInformationModel(
        entity: WeatherInformationEntity
    ): WeatherInformationModel {
        return WeatherInformationModel(
            id = entity.weatherInformationStatusId,
            status = entity.status,
            description = entity.description,
            icon = entity.icon
        )
    }

    // Network to Presentation model converters
    fun fiveDayThreeHourForecastResponseToFiveDayThreeHourForecastModel(
        response: FiveDayThreeHourForecastResponseModel
    ): FiveDayThreeHourForecastModel {
        return FiveDayThreeHourForecastModel(
            cnt = response.cnt,
            cityInformation = CityInformationModel(
                id = response.cityInformation.id,
                cityName = response.cityInformation.cityName,
                cityCoordinates = CityCoordinatesModel(
                    latitude = response.cityInformation.cityCoordinates.latitude,
                    longitude = response.cityInformation.cityCoordinates.longitude,
                ),
                country = response.cityInformation.country,
                population = response.cityInformation.population,
                sunrise = response.cityInformation.sunrise,
                sunset = response.cityInformation.sunset,
                timezone = response.cityInformation.timezone,
            ),
            forecastModels = response.forecastResponseModels.map {
                forecastResponseToForecastModel(response = it)
            }
        )
    }

    private fun forecastResponseToForecastModel(response: ForecastResponseModel): ForecastModel {
        return ForecastModel(
            timeOfData = response.timeOfData,
            weatherConditionInformation = WeatherConditionInformationModel(
                temperature = response.weatherConditionInformation.temperature,
                feelsLike = response.weatherConditionInformation.feelsLike,
                minimumTemperature = response.weatherConditionInformation.minimumTemperature,
                maximumTemperature = response.weatherConditionInformation.maximumTemperature,
                pressure = response.weatherConditionInformation.pressure,
                seaLevelPressure = response.weatherConditionInformation.seaLevelPressure,
                groundLevelPressure = response.weatherConditionInformation.groundLevelPressure,
                humidity = response.weatherConditionInformation.humidity,
                tempKf = response.weatherConditionInformation.tempKf,
            ),
            cloudsInformation = CloudsInformationModel(
                cloudiness = response.cloudsInformation.cloudiness,
            ),
            windInformation = WindInformationModel(
                speed = response.windInformation.speed,
                degree = response.windInformation.degree,
                gust = response.windInformation.gust,
            ),
            rainInformation = RainInformationModel(
                threeHour = response.rainInformation.threeHour,
            ),
            snowInformation = SnowInformationModel(
                threeHour = response.snowInformation.threeHour,
            ),
            dayInformation = DayInformationModel(
                dayStatus = response.dayInformation.dayStatus,
            ),
            visibility = response.visibility,
            probabilityOfPrecipitation = response.probabilityOfPrecipitation,
            timeOfDataInReadableFormat = response.timeOfDataInReadableFormat,
            weatherInformation = response.weatherInformation.map {
                weatherInformationResponseToWeatherInformationModel(response = it)
            }
        )
    }

    private fun weatherInformationResponseToWeatherInformationModel(
        response: WeatherInformationResponseModel
    ): WeatherInformationModel {
        return WeatherInformationModel(
            id = response.id,
            status = response.status,
            description = response.description,
            icon = response.icon
        )
    }
}
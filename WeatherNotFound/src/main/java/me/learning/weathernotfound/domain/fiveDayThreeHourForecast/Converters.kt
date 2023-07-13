package me.learning.weathernotfound.domain.fiveDayThreeHourForecast

import me.learning.weathernotfound.BuildConfig
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
import me.learning.weathernotfound.utils.Utilities

internal object Converters {

    // Network to Database entity converters
    fun fiveDayThreeHourForecastResponseToFiveDayThreeHourForecastEntity(
        response: FiveDayThreeHourForecastResponseModel,
        entityId: Long? = null,
    ): FiveDayThreeHourForecastEntity {
        val result = FiveDayThreeHourForecastEntity(
            id = entityId,
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
            createdAt = Utilities.getCurrentDateTime(),
            updatedAt = Utilities.getCurrentDateTime(),
        )

        validateFiveDayThreeHourForecastResponseToForecastEntityConversion(
            responseModel = response,
            entityModel = result
        )

        return result
    }

    fun forecastResponseToForecastEntity(
        responses: List<ForecastResponseModel>,
        forecastId: Long,
    ): List<ForecastEntity> {
        val result = responses.map {
            ForecastEntity(
                id = null,
                fiveDayForecastId = forecastId,
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
                speed = it.windInformation?.speed,
                degree = it.windInformation?.degree,
                gust = it.windInformation?.gust,
                threeHourRainInformation = it.rainInformation?.threeHour,
                threeHourSnowInformation = it.snowInformation?.threeHour,
                dayStatus = it.dayInformation.dayStatus,
                visibility = it.visibility,
                probabilityOfPrecipitation = it.probabilityOfPrecipitation,
                timeOfDataInReadableFormat = it.timeOfDataInReadableFormat
            )
        }

        validateForecastResponseModelsToForecastEntityModelsConversion(
            responseModels = responses,
            entityModels = result
        )

        return result
    }

    fun weatherInformationResponseToWeatherInformationEntity(
        responses: List<WeatherInformationResponseModel>,
        forecastId: Long
    ): List<WeatherInformationEntity> {
        val result = responses.map {
            WeatherInformationEntity(
                id = null,
                forecastId = forecastId,
                weatherInformationStatusId = it.id,
                status = it.status,
                description = it.description,
                icon = it.icon
            )
        }

        validateWeatherInformationResponseModelToWeatherInformationEntityModelConversion(
            responseModels = responses,
            entityModels = result
        )

        return result
    }

    // Database to Presentation model converters
    fun fiveDayThreeOurForecastEntityToFiveDayThreeHourForecastModel(
        fiveDayEntity: FiveDayThreeHourForecastEntity,
        forecastEntities: List<ForecastEntity>,
        weatherInformationEntities: List<List<WeatherInformationEntity>>
    ): FiveDayThreeHourForecastModel {
        val result = FiveDayThreeHourForecastModel(
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
            forecastModels = forecastEntities.mapIndexed { index, forecastEntity ->
                forecastEntityToForecastModel(
                    entity = forecastEntity,
                    weatherInformation = weatherInformationEntities[index]
                )
            }
        )

        validateEntityModelToPresentationModelConversion(
            fiveDayThreeHourEntityModel = fiveDayEntity,
            forecastEntityModels = forecastEntities,
            weatherInformationEntityModels = weatherInformationEntities,
            presentationModel = result
        )

        return result
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
        val result = FiveDayThreeHourForecastModel(
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

        validateNetworkResponseToPresentationModelConversion(
            responseModel = response,
            presentationModel = result
        )

        return result
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
                speed = response.windInformation?.speed,
                degree = response.windInformation?.degree,
                gust = response.windInformation?.gust,
            ),
            rainInformation = RainInformationModel(
                threeHour = response.rainInformation?.threeHour,
            ),
            snowInformation = SnowInformationModel(
                threeHour = response.snowInformation?.threeHour,
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

    // Todo: tests should be in the test section! for now, we just check like this:
    private fun validateNetworkResponseToPresentationModelConversion(
        responseModel: FiveDayThreeHourForecastResponseModel,
        presentationModel: FiveDayThreeHourForecastModel
    ) {
        if (!BuildConfig.DEBUG) return
        assert(responseModel.cnt == presentationModel.cnt)
        assert(responseModel.cityInformation.id == presentationModel.cityInformation.id)
        assert(responseModel.cityInformation.cityName == presentationModel.cityInformation.cityName)
        assert(responseModel.cityInformation.cityCoordinates.latitude == presentationModel.cityInformation.cityCoordinates.latitude)
        assert(responseModel.cityInformation.cityCoordinates.longitude == presentationModel.cityInformation.cityCoordinates.longitude)
        assert(responseModel.cityInformation.country == presentationModel.cityInformation.country)
        assert(responseModel.cityInformation.population == presentationModel.cityInformation.population)
        assert(responseModel.cityInformation.sunrise == presentationModel.cityInformation.sunrise)
        assert(responseModel.cityInformation.sunset == presentationModel.cityInformation.sunset)
        assert(responseModel.cityInformation.timezone == presentationModel.cityInformation.timezone)

        responseModel.forecastResponseModels.forEachIndexed { index, forecastResponseModel ->
            val forecastPresentationModel = presentationModel.forecastModels[index]

            assert(forecastResponseModel.timeOfData == forecastPresentationModel.timeOfData)
            assert(forecastResponseModel.weatherConditionInformation.temperature == forecastPresentationModel.weatherConditionInformation.temperature)
            assert(forecastResponseModel.weatherConditionInformation.feelsLike == forecastPresentationModel.weatherConditionInformation.feelsLike)
            assert(forecastResponseModel.weatherConditionInformation.minimumTemperature == forecastPresentationModel.weatherConditionInformation.minimumTemperature)
            assert(forecastResponseModel.weatherConditionInformation.maximumTemperature == forecastPresentationModel.weatherConditionInformation.maximumTemperature)
            assert(forecastResponseModel.weatherConditionInformation.pressure == forecastPresentationModel.weatherConditionInformation.pressure)
            assert(forecastResponseModel.weatherConditionInformation.seaLevelPressure == forecastPresentationModel.weatherConditionInformation.seaLevelPressure)
            assert(forecastResponseModel.weatherConditionInformation.groundLevelPressure == forecastPresentationModel.weatherConditionInformation.groundLevelPressure)
            assert(forecastResponseModel.weatherConditionInformation.humidity == forecastPresentationModel.weatherConditionInformation.humidity)
            assert(forecastResponseModel.weatherConditionInformation.tempKf == forecastPresentationModel.weatherConditionInformation.tempKf)
            assert(forecastResponseModel.cloudsInformation.cloudiness == forecastPresentationModel.cloudsInformation.cloudiness)

            if (forecastResponseModel.windInformation == null) {
                assert(forecastPresentationModel.windInformation.gust == null)
                assert(forecastPresentationModel.windInformation.degree == null)
                assert(forecastPresentationModel.windInformation.speed == null)
            } else {
                assert(forecastResponseModel.windInformation.speed == forecastPresentationModel.windInformation.speed)
                assert(forecastResponseModel.windInformation.degree == forecastPresentationModel.windInformation.degree)
                assert(forecastResponseModel.windInformation.gust == forecastPresentationModel.windInformation.gust)
            }
            if (forecastResponseModel.rainInformation == null) {
                assert(forecastPresentationModel.rainInformation.threeHour == null)
            } else {
                assert(forecastResponseModel.rainInformation.threeHour == forecastPresentationModel.rainInformation.threeHour)
            }
            if (forecastResponseModel.snowInformation == null) {
                assert(forecastPresentationModel.snowInformation.threeHour == null)
            } else {
                assert(forecastResponseModel.snowInformation.threeHour == forecastPresentationModel.snowInformation.threeHour)
            }

            assert(forecastResponseModel.dayInformation.dayStatus == forecastPresentationModel.dayInformation.dayStatus)
            assert(forecastResponseModel.visibility == forecastPresentationModel.visibility)
            assert(forecastResponseModel.probabilityOfPrecipitation == forecastPresentationModel.probabilityOfPrecipitation)
            assert(forecastResponseModel.timeOfDataInReadableFormat == forecastPresentationModel.timeOfDataInReadableFormat)

            forecastResponseModel.weatherInformation.forEachIndexed { index, weatherInformationResponseModel ->
                val weatherInformationPresentationModel =
                    forecastPresentationModel.weatherInformation[index]

                assert(weatherInformationResponseModel.id == weatherInformationPresentationModel.id)
                assert(weatherInformationResponseModel.status == weatherInformationPresentationModel.status)
                assert(weatherInformationResponseModel.description == weatherInformationPresentationModel.description)
                assert(weatherInformationResponseModel.icon == weatherInformationPresentationModel.icon)
            }
        }
    }


    private fun validateEntityModelToPresentationModelConversion(
        fiveDayThreeHourEntityModel: FiveDayThreeHourForecastEntity,
        forecastEntityModels: List<ForecastEntity>,
        weatherInformationEntityModels: List<List<WeatherInformationEntity>>,
        presentationModel: FiveDayThreeHourForecastModel
    ) {
        if (!BuildConfig.DEBUG) return
        assert(fiveDayThreeHourEntityModel.cnt == presentationModel.cnt)
        assert(fiveDayThreeHourEntityModel.cityName == presentationModel.cityInformation.cityName)
        assert(fiveDayThreeHourEntityModel.latitude == presentationModel.cityInformation.cityCoordinates.latitude)
        assert(fiveDayThreeHourEntityModel.longitude == presentationModel.cityInformation.cityCoordinates.longitude)
        assert(fiveDayThreeHourEntityModel.country == presentationModel.cityInformation.country)
        assert(fiveDayThreeHourEntityModel.population == presentationModel.cityInformation.population)
        assert(fiveDayThreeHourEntityModel.sunrise == presentationModel.cityInformation.sunrise)
        assert(fiveDayThreeHourEntityModel.sunset == presentationModel.cityInformation.sunset)
        assert(fiveDayThreeHourEntityModel.timezone == presentationModel.cityInformation.timezone)

        forecastEntityModels.forEachIndexed { index, forecastEntity ->
            val presentationForecastModel = presentationModel.forecastModels[index]

            assert(fiveDayThreeHourEntityModel.id == forecastEntity.fiveDayForecastId)
            assert(forecastEntity.timeOfData == presentationForecastModel.timeOfData)
            assert(forecastEntity.temperature == presentationForecastModel.weatherConditionInformation.temperature)
            assert(forecastEntity.feelsLike == presentationForecastModel.weatherConditionInformation.feelsLike)
            assert(forecastEntity.minimumTemperature == presentationForecastModel.weatherConditionInformation.minimumTemperature)
            assert(forecastEntity.maximumTemperature == presentationForecastModel.weatherConditionInformation.maximumTemperature)
            assert(forecastEntity.pressure == presentationForecastModel.weatherConditionInformation.pressure)
            assert(forecastEntity.seaLevelPressure == presentationForecastModel.weatherConditionInformation.seaLevelPressure)
            assert(forecastEntity.groundLevelPressure == presentationForecastModel.weatherConditionInformation.groundLevelPressure)
            assert(forecastEntity.humidity == presentationForecastModel.weatherConditionInformation.humidity)
            assert(forecastEntity.tempKf == presentationForecastModel.weatherConditionInformation.tempKf)
            assert(forecastEntity.cloudiness == presentationForecastModel.cloudsInformation.cloudiness)

            if (forecastEntity.speed != null) {
                assert(forecastEntity.speed == presentationForecastModel.windInformation.speed)
            } else {
                assert(presentationForecastModel.windInformation.speed == null)
            }

            if (forecastEntity.degree != null) {
                assert(forecastEntity.degree == presentationForecastModel.windInformation.degree)
            } else {
                assert(presentationForecastModel.windInformation.degree == null)
            }

            if (forecastEntity.gust != null) {
                assert(forecastEntity.gust == presentationForecastModel.windInformation.gust)
            } else {
                assert(presentationForecastModel.windInformation.gust == null)
            }

            if (forecastEntity.threeHourRainInformation != null) {
                assert(forecastEntity.threeHourRainInformation == presentationForecastModel.rainInformation.threeHour)
            } else {
                assert(presentationForecastModel.rainInformation.threeHour == null)
            }

            if (forecastEntity.threeHourSnowInformation != null) {
                assert(forecastEntity.threeHourSnowInformation == presentationForecastModel.snowInformation.threeHour)
            } else {
                assert(presentationForecastModel.snowInformation.threeHour == null)
            }

            assert(forecastEntity.dayStatus == presentationForecastModel.dayInformation.dayStatus)
            assert(forecastEntity.visibility == presentationForecastModel.visibility)
            assert(forecastEntity.probabilityOfPrecipitation == presentationForecastModel.probabilityOfPrecipitation)
            assert(forecastEntity.timeOfDataInReadableFormat == presentationForecastModel.timeOfDataInReadableFormat)

            weatherInformationEntityModels[index].forEachIndexed { index, weatherInformationEntity ->
                val weatherInformationPresentationModel =
                    presentationForecastModel.weatherInformation[index]

                assert(weatherInformationEntity.forecastId == forecastEntity.id)
                assert(weatherInformationEntity.weatherInformationStatusId == weatherInformationPresentationModel.id)
                assert(weatherInformationEntity.status == weatherInformationPresentationModel.status)
                assert(weatherInformationEntity.description == weatherInformationPresentationModel.description)
                assert(weatherInformationEntity.icon == weatherInformationPresentationModel.icon)
            }
        }
    }

    private fun validateFiveDayThreeHourForecastResponseToForecastEntityConversion(
        responseModel: FiveDayThreeHourForecastResponseModel,
        entityModel: FiveDayThreeHourForecastEntity
    ) {
        if (!BuildConfig.DEBUG) return

        assert(entityModel.cnt == responseModel.cnt)
        assert(entityModel.cityId == responseModel.cityInformation.id)
        assert(entityModel.cityName == responseModel.cityInformation.cityName)
        assert(entityModel.latitude == responseModel.cityInformation.cityCoordinates.latitude)
        assert(entityModel.longitude == responseModel.cityInformation.cityCoordinates.longitude)
        assert(entityModel.country == responseModel.cityInformation.country)
        assert(entityModel.population == responseModel.cityInformation.population)
        assert(entityModel.sunrise == responseModel.cityInformation.sunrise)
        assert(entityModel.sunset == responseModel.cityInformation.sunset)
        assert(entityModel.timezone == responseModel.cityInformation.timezone)
    }


    private fun validateForecastResponseModelsToForecastEntityModelsConversion(
        responseModels: List<ForecastResponseModel>,
        entityModels: List<ForecastEntity>
    ) {
        entityModels.forEachIndexed { index, forecastEntity ->
            val forecastResponseModel = responseModels[index]

            assert(forecastEntity.timeOfData == forecastResponseModel.timeOfData)
            assert(forecastEntity.temperature == forecastResponseModel.weatherConditionInformation.temperature)
            assert(forecastEntity.feelsLike == forecastResponseModel.weatherConditionInformation.feelsLike)
            assert(forecastEntity.minimumTemperature == forecastResponseModel.weatherConditionInformation.minimumTemperature)
            assert(forecastEntity.maximumTemperature == forecastResponseModel.weatherConditionInformation.maximumTemperature)
            assert(forecastEntity.pressure == forecastResponseModel.weatherConditionInformation.pressure)
            assert(forecastEntity.seaLevelPressure == forecastResponseModel.weatherConditionInformation.seaLevelPressure)
            assert(forecastEntity.groundLevelPressure == forecastResponseModel.weatherConditionInformation.groundLevelPressure)
            assert(forecastEntity.humidity == forecastResponseModel.weatherConditionInformation.humidity)
            assert(forecastEntity.tempKf == forecastResponseModel.weatherConditionInformation.tempKf)
            assert(forecastEntity.cloudiness == forecastResponseModel.cloudsInformation.cloudiness)

            if (forecastResponseModel.windInformation == null) {
                assert(forecastEntity.speed == null)
                assert(forecastEntity.gust == null)
                assert(forecastEntity.degree == null)
            } else {
                assert(forecastEntity.speed == forecastResponseModel.windInformation.speed)
                assert(forecastEntity.degree == forecastResponseModel.windInformation.degree)
                assert(forecastEntity.gust == forecastResponseModel.windInformation.gust)
            }

            if (forecastResponseModel.rainInformation == null) {
                assert(forecastEntity.threeHourRainInformation == null)
            } else {
                assert(forecastEntity.threeHourRainInformation == forecastResponseModel.rainInformation.threeHour)
            }

            if (forecastResponseModel.snowInformation == null) {
                assert(forecastEntity.threeHourSnowInformation == null)
            } else {
                assert(forecastEntity.threeHourSnowInformation == forecastResponseModel.snowInformation.threeHour)
            }

            assert(forecastEntity.dayStatus == forecastResponseModel.dayInformation.dayStatus)
            assert(forecastEntity.visibility == forecastResponseModel.visibility)
            assert(forecastEntity.probabilityOfPrecipitation == forecastResponseModel.probabilityOfPrecipitation)
            assert(forecastEntity.timeOfDataInReadableFormat == forecastResponseModel.timeOfDataInReadableFormat)
        }
    }

    private fun validateWeatherInformationResponseModelToWeatherInformationEntityModelConversion(
        responseModels: List<WeatherInformationResponseModel>,
        entityModels: List<WeatherInformationEntity>
    ) {
        entityModels.forEachIndexed { index, weatherInformationEntity ->
            val weatherInformationResponseModel = responseModels[index]

            assert(weatherInformationEntity.weatherInformationStatusId == weatherInformationResponseModel.id)
            assert(weatherInformationEntity.status == weatherInformationResponseModel.status)
            assert(weatherInformationEntity.description == weatherInformationResponseModel.description)
            assert(weatherInformationEntity.icon == weatherInformationResponseModel.icon)
        }
    }
}
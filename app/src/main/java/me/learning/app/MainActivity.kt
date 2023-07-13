package me.learning.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import me.learning.weathernotfound.domain.currentWeather.presentationModels.CurrentWeatherModel
import me.learning.weathernotfound.domain.fiveDayThreeHourForecast.presentationModels.FiveDayThreeHourForecastModel
import me.learning.weathernotfound.presentation.WeatherNotFound
import me.learning.weathernotfound.presentation.WeatherNotFoundCallback
import me.learning.weathernotfound.presentation.WeatherNotFoundError
import me.learning.weathernotfound.presentation.WeatherNotFoundResponse

class MainActivity : AppCompatActivity() {

    private lateinit var currentWeatherMaterialButton: MaterialButton
    private lateinit var fiveDayThreeHourMaterialButton: MaterialButton
    private lateinit var invalidateFiveDayThreeHourMaterialButton: MaterialButton
    private lateinit var invalidateCurrentWeatherMaterialButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        currentWeatherMaterialButton.setOnClickListener { startCurrentWeatherRequest() }

        fiveDayThreeHourMaterialButton.setOnClickListener { startFiveDayThreeHourRequest() }

        invalidateFiveDayThreeHourMaterialButton.setOnClickListener {
            WeatherNotFound.getInstance().invalidateFiveDayThreeHourForecastCache()
        }

        invalidateCurrentWeatherMaterialButton.setOnClickListener {
            WeatherNotFound.getInstance().invalidateCurrentWeatherCache()
        }
    }

    private fun initViews() {
        currentWeatherMaterialButton = findViewById(R.id.currentWeatherMaterialButton)
        fiveDayThreeHourMaterialButton = findViewById(R.id.fiveDayThreeHourMaterialButton)
        invalidateFiveDayThreeHourMaterialButton =
            findViewById(R.id.invalidateFiveDayThreeHourMaterialButton)
        invalidateCurrentWeatherMaterialButton =
            findViewById(R.id.invalidateCurrentWeatherMaterialButton)
    }

    private fun startCurrentWeatherRequest() {
        WeatherNotFound.getInstance().getCurrentWeatherInformation(
            latitude = 0.0,
            longitude = 0.0,
            weatherNotFoundCallback = object :
                WeatherNotFoundCallback<WeatherNotFoundResponse<CurrentWeatherModel>, WeatherNotFoundError> {
                override fun onSuccess(response: WeatherNotFoundResponse<CurrentWeatherModel>) {
                    // Do whatever you want...
                }

                override fun onError(error: WeatherNotFoundError) {
                    error.exception?.printStackTrace()
                }
            }
        )
    }

    private fun startFiveDayThreeHourRequest() {
        WeatherNotFound.getInstance().getFiveDayThreeHourForecastInformation(
            latitude = 0.0,
            longitude = 0.0,
            weatherNotFoundCallback = object :
                WeatherNotFoundCallback<WeatherNotFoundResponse<FiveDayThreeHourForecastModel>, WeatherNotFoundError> {
                override fun onSuccess(response: WeatherNotFoundResponse<FiveDayThreeHourForecastModel>) {
                    // Do whatever you want...
                }

                override fun onError(error: WeatherNotFoundError) {
                    error.exception?.printStackTrace()
                }
            }
        )
    }

}
package me.learning.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import me.learning.weathernotfound.domain.currentWeather.presentationModels.CurrentWeatherModel
import me.learning.weathernotfound.presentation.WeatherNotFound
import me.learning.weathernotfound.presentation.WeatherNotFoundCallback
import me.learning.weathernotfound.presentation.WeatherNotFoundError
import me.learning.weathernotfound.presentation.WeatherNotFoundResponse

class MainActivity : AppCompatActivity() {

    private lateinit var currentWeatherMaterialButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        currentWeatherMaterialButton.setOnClickListener { startCurrentWeatherRequest() }
    }

    private fun initViews() {
        currentWeatherMaterialButton = findViewById(R.id.currentWeatherMaterialButton)
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
}
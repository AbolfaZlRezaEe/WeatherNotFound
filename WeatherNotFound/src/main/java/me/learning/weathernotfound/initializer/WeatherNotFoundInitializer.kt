package me.learning.weathernotfound.initializer

import android.content.Context
import androidx.startup.Initializer
import me.learning.weathernotfound.presentation.WeatherNotFound


class WeatherNotFoundInitializer : Initializer<WeatherNotFound> {
    override fun create(context: Context): WeatherNotFound {
        return WeatherNotFound.getInstance().apply {
            tryAutoInit(context)
        }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}
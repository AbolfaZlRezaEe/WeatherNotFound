package me.learning.app

import android.app.Application
import me.learning.weathernotfound.presentation.WeatherNotFound
import okhttp3.logging.HttpLoggingInterceptor

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        WeatherNotFound.getInstance().init(
            context = this,
            httpLoggingLevel = HttpLoggingInterceptor.Level.BODY,
            cacheMechanismEnabled = true
        )
    }
}
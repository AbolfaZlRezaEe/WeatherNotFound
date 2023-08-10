package me.learning.weathernotfound.presentation

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import androidx.startup.Initializer

internal class AutoInitializer : Initializer<WeatherNotFound> {
    internal companion object {
        private const val MANIFEST_METADATA_KEY_AUTO_INIT_ENABLED =
            "weather_not_found.auto_init_enabled"

        private const val MANIFEST_METADATA_KEY_OPEN_WEATHER_MAP_APIKEY =
            "weather_not_found.open_weather_api_key"
    }

    override fun create(context: Context): WeatherNotFound {
        return WeatherNotFound.getInstance().apply {
            if (autoInitEnabled(context = context)) {
                autoInit(
                    context = context,
                    openWeatherApiKey = getOpenWeatherApiKey(context = context)
                )
            }
        }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()

    private fun getApplicationInfo(context: Context): ApplicationInfo {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.packageManager.getApplicationInfo(
                context.packageName,
                PackageManager.ApplicationInfoFlags.of(PackageManager.GET_META_DATA.toLong())
            )
        } else {
            context.packageManager
                .getApplicationInfo(
                    context.packageName,
                    PackageManager.GET_META_DATA
                )
        }
    }

    private fun autoInitEnabled(
        context: Context,
        defaultValue: Boolean = false
    ):Boolean{
        return getApplicationInfo(context).metaData.getBoolean(
            MANIFEST_METADATA_KEY_AUTO_INIT_ENABLED,
            defaultValue
        )
    }

    private fun getOpenWeatherApiKey(
        context: Context,
        defaultValue: String = ""
    ): String {
        return getApplicationInfo(context).metaData.getString(
            MANIFEST_METADATA_KEY_OPEN_WEATHER_MAP_APIKEY,
            defaultValue
        )
    }
}
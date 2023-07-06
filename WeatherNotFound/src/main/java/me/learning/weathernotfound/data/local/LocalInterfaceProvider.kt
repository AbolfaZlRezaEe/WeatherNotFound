package me.learning.weathernotfound.data.local

import android.content.Context
import androidx.room.Room

internal object LocalInterfaceProvider {
    private var DATABASE_INSTANCE: WeatherNotFoundDatabase? = null

    private const val DATABASE_NAME = "WeatherNotFound_db"

    const val DATABASE_VERSION = 1

    private var cacheMechanism = false

    @Synchronized
    fun init(context: Context) {
        if (DATABASE_INSTANCE == null) {
            DATABASE_INSTANCE = Room.databaseBuilder(
                context = context,
                klass = WeatherNotFoundDatabase::class.java,
                name = DATABASE_NAME
            ).build()
        }
    }

    @Synchronized
    fun setCacheMechanism(enabled: Boolean) {
        this.cacheMechanism = enabled
    }

    fun isCacheMechanismEnabled(): Boolean {
        return this.cacheMechanism
    }

    fun isCacheMechanismDisabled(): Boolean {
        return !this.cacheMechanism
    }

    fun getDatabaseInstance(): WeatherNotFoundDatabase? {
        if (isCacheMechanismEnabled() && DATABASE_INSTANCE == null){
            throw NullPointerException("Database initialization failed. check provider please!")
        }
        return DATABASE_INSTANCE
    }
}
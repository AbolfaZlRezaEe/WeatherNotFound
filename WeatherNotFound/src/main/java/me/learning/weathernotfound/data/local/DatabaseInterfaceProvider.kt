package me.learning.weathernotfound.data.local

import android.content.Context
import androidx.room.Room

internal object DatabaseInterfaceProvider {
    private var DATABASE_INSTANCE: WeatherNotFoundDatabase? = null

    private const val DATABASE_NAME = "WeatherNotFound_db"

    const val DATABASE_VERSION = 1

    @Synchronized
    fun init(context: Context) {
        if (DATABASE_INSTANCE == null) {
            DATABASE_INSTANCE = Room.databaseBuilder(
                context = context,
                klass = WeatherNotFoundDatabase::class.java,
                name = DATABASE_NAME
            ).addTypeConverter(DatabaseTypeConverter::class.java).build()
        }
    }

    fun getDatabaseInstance(): WeatherNotFoundDatabase {
        return DATABASE_INSTANCE
            ?: throw NullPointerException("Database initialization failed. check provider please!")
    }
}
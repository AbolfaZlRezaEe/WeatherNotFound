package me.learning.weathernotfound.data.local

import android.content.Context
import androidx.room.Room

/**
 * Provide and manage everything that are related to the local database in library.
 */
internal object LocalInterfaceProvider {
    private var DATABASE_INSTANCE: WeatherNotFoundDatabase? = null

    private const val DATABASE_NAME = "WeatherNotFound_db"

    const val DATABASE_VERSION = 1

    private var cacheMechanism = false

    /**
     * With calling this, [LocalInterfaceProvider] will instantiate singleton instance of WeatherNotFound database.
     * @param context used for build and access to the database.
     */
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

    /**
     * With setting [enabled] to true or false, you turn **on** or **off** the database mechanism.
     */
    @Synchronized
    fun setCacheMechanism(enabled: Boolean) {
        this.cacheMechanism = enabled
    }

    /**
     * @return true if cache mechanism is enabled, false otherwise.
     */
    fun isCacheMechanismEnabled(): Boolean {
        return this.cacheMechanism
    }

    /**
     * @return true if cache mechanism is disabled, false otherwise.
     */
    fun isCacheMechanismDisabled(): Boolean {
        return !this.cacheMechanism
    }

    /**
     * @return a singleton instance of [WeatherNotFoundDatabase] if you called [init] function before,
     * null otherwise.
     *
     * @throws [NullPointerException] if [cacheMechanism] is enable but you didn't call [init] function!
     */
    fun getDatabaseInstance(): WeatherNotFoundDatabase? {
        if (isCacheMechanismEnabled() && DATABASE_INSTANCE == null){
            throw NullPointerException("Database initialization failed. check provider please!")
        }
        return DATABASE_INSTANCE
    }
}
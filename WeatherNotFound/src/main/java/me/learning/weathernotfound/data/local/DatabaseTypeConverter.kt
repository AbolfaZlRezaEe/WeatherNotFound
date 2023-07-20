package me.learning.weathernotfound.data.local

import androidx.room.TypeConverter
import java.util.Date

internal class DatabaseTypeConverter {

    /**
     * Convert given [date] object to the long datatype for storing it into the database.
     */
    @TypeConverter
    fun dateToTimeStamp(date: Date): Long {
        return date.time
    }

    /**
     * Convert given [timeStamp] to the [Date] object after we receive data from database.
     */
    @TypeConverter
    fun timeStampToDate(timeStamp: Long): Date {
        return Date(timeStamp)
    }
}
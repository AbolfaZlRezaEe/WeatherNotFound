package me.learning.weathernotfound.data.local

import androidx.room.TypeConverter
import java.util.Date

internal class DatabaseTypeConverter {

    @TypeConverter
    fun dateToTimeStamp(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun timeStampToDate(timeStamp: Long): Date {
        return Date(timeStamp)
    }
}
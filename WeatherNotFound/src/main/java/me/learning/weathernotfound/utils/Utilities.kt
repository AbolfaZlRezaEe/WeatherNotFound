package me.learning.weathernotfound.utils

import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit

object Utilities {

    fun Date.halfDayPassed(): Boolean {
        return (System.currentTimeMillis() - this.time) >= TimeUnit.DAYS.toMillis(1) / 2
    }

    fun Date.threeDayPassed(): Boolean {
        return (System.currentTimeMillis() - this.time) >= TimeUnit.DAYS.toMillis(3)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }
}
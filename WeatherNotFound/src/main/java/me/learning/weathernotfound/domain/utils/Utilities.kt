package me.learning.weathernotfound.domain.utils

import java.util.Calendar
import java.util.Date

object Utilities {
     fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }
}
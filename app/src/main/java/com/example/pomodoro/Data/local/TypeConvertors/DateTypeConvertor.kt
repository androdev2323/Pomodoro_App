package com.example.pomodoro.Data.local.TypeConvertors

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
/*
class DateTypeConvertor {
    private val format=SimpleDateFormat("yyyy-MM-dd",Locale.getDefault())

    @TypeConverter
    fun fromlong(long: Long?): String? {
        return long?.let {
            format.format(long)
        }
    }

        fun toLong(datestring:String):Long?{
            return datestring.let {
                format.parse(it)?.time
            }
        }

}
*/

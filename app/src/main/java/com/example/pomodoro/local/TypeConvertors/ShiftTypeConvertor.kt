package com.example.pomodoro.local.TypeConvertors

import androidx.room.TypeConverter
import com.example.pomodoro.local.ShiftType

class ShiftTypeConvertor {
    @TypeConverter
    fun fromSessiontype(sessiontyp: ShiftType) :String {
   return sessiontyp.name
    }

    @TypeConverter
    fun toSessionType(type:String): ShiftType {
      return ShiftType.valueOf(type)
    }
}
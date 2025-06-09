package com.example.pomodoro.presentation.HomeScreen.Entity

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class CalendarUi(val selecteddate: Date, val visbledates: List<Date>) {
    val startdate=visbledates.first();
    val enddate=visbledates.last()

    data class Date(val date: LocalDate,
        val isSelected:Boolean,
        val isToday:Boolean){
        val day:String=date.format(DateTimeFormatter.ofPattern("E"))
    }


}
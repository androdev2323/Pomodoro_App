package com.example.pomodoro.Util

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Locale

object TimeFormatter {
    private var curr= Locale.getDefault()
private var format = SimpleDateFormat("yyyy-MM-dd", curr)
    fun longtoString(date:Long):String{
        val next = Locale.getDefault()
        if(next!= curr){
            curr= next;
            format = SimpleDateFormat("yyyy-MM-dd", curr)
        }
        return format.format(date)
    }
}
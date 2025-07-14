package com.example.pomodoro.Util

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Locale
import java.util.concurrent.TimeUnit

object TimeFormatter {
    private var curr= Locale.getDefault()
private var format = SimpleDateFormat("yyyy-MM-dd", curr)
    private var timeformat = SimpleDateFormat("mm:ss", curr)
    fun longtoString(date:Long):String{
        val next = Locale.getDefault()
        if(next!= curr){
            curr= next;
            format = SimpleDateFormat("yyyy-MM-dd", curr)
        }
        return format.format(date)
    }

    fun longtoTime(time:Long):String{
    val  seconds = TimeUnit.MILLISECONDS.toSeconds(time)
        val minutes = seconds/60
        val seco = seconds % 60

        return String.format("%02d:%02d", minutes, seconds)
    }
}
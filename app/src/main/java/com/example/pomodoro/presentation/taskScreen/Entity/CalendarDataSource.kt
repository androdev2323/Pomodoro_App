package com.example.pomodoro.presentation.HomeScreen.Entity

import android.util.Log
import java.time.DayOfWeek

import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.stream.Collectors
import java.util.stream.Stream


class CalendarDataSource(){
    val today:LocalDate

        get() {
          return LocalDate.now()
        }
    fun getDate(starDate:LocalDate=today,lastselectedate:LocalDate):List<LocalDate> {
        val firstdayofweek = starDate.with(DayOfWeek.MONDAY)
        val enddayofweek = firstdayofweek.plusDays(7)
        val getdatesinbetween=getinbetween(firstdayofweek,enddayofweek)

        return getdatesinbetween

    }
    fun getinbetween(startDate: LocalDate, endDate:LocalDate):List<LocalDate>{
        val numofdays=ChronoUnit.DAYS.between(startDate,endDate)
        return Stream.iterate(startDate){stardate->
                stardate.plusDays(1)
        }
            .limit(numofdays)
            .collect(Collectors.toList())
    }

}
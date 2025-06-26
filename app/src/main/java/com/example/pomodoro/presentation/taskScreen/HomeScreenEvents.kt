package com.example.pomodoro.presentation.HomeScreen

import java.time.LocalDate

sealed class HomeScreenEvents {

    data class getDates(
        val startdate: LocalDate = LocalDate.now(),
        val lastselectedDate: LocalDate
    ) : HomeScreenEvents()

    data class GetTasks(val date: LocalDate) : HomeScreenEvents()


}
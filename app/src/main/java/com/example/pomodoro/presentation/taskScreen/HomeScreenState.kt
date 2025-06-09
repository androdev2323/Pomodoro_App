package com.example.pomodoro.presentation.HomeScreen

import com.example.pomodoro.presentation.HomeScreen.Entity.CalendarUi

sealed interface HomeScreenState {

    data class Success(
        val dates:CalendarUi
    ) : HomeScreenState
    object Loadng : HomeScreenState
    data class Error(val message:String?) : HomeScreenState


}
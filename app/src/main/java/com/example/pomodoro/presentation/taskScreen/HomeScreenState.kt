package com.example.pomodoro.presentation.HomeScreen

import com.example.pomodoro.Data.local.Entity.Task
import com.example.pomodoro.presentation.HomeScreen.Entity.CalendarUi

data class HomeScreenState(


    val dates: CalendarUi? = null,
    val taskList: List<Task> = emptyList()
)




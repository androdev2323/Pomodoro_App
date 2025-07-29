package com.example.pomodoro.presentation.StopWatch

import com.example.pomodoro.Data.local.Entity.Task


data class  StopwatchScreenState(
    val taskitem:Task?,
    val timerState:StopWatchState,

)
interface StopWatchState{
    data class Pause(val time:Long):StopWatchState
    data class Running(val time:Long):StopWatchState
    data object  Finished:StopWatchState
}

data class ViewmodelState(
    val isEnabled:Boolean = true
)
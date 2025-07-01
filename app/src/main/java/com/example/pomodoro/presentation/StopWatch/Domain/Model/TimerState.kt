package com.example.pomodoro.presentation.StopWatch.Domain.Model

sealed class TimerState{
     object idle:TimerState()
    data class Running(val time:Long):TimerState()
    data class Paused(val remainingtime:Long):TimerState()
    object Finished:TimerState()
}

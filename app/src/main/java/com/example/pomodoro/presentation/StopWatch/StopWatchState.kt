package com.example.pomodoro.presentation.StopWatch

interface StopWatchState{
    data class Pause(val time:Long):StopWatchState
    data class Running(val time:Long):StopWatchState
    data object Finished:StopWatchState
}
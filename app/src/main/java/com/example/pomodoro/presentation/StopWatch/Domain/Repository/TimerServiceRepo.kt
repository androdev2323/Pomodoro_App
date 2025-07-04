package com.example.pomodoro.presentation.StopWatch.Domain.Repository

import com.example.pomodoro.presentation.StopWatch.Domain.Model.TimerState
import kotlinx.coroutines.flow.Flow

interface TimerServiceRepo {

    fun getTimerState(): Flow<TimerState>
    fun startTimer(duration:Long)
    fun pauseTimer()

}
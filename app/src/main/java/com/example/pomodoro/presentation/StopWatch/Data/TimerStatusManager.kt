package com.example.pomodoro.presentation.StopWatch.Data

import com.example.pomodoro.presentation.StopWatch.Domain.Model.TimerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class  TimerStatusManager @Inject constructor(){
    private val _timerstate = MutableStateFlow<TimerState?>(null)
    val timerState = _timerstate.asStateFlow()

    fun updatestate(state:TimerState){
        _timerstate.value = state
    }
}
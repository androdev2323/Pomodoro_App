package com.example.pomodoro.presentation.StopWatch

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pomodoro.presentation.StopWatch.Data.service.PomodoroTimerService
import com.example.pomodoro.presentation.StopWatch.Domain.Model.TimerState
import com.example.pomodoro.presentation.StopWatch.Domain.Repository.TimerServiceRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class StopWatchViewmodel @Inject constructor( val serviceRepo: TimerServiceRepo,):ViewModel() {

 init {
     serviceRepo.startTimer(25000)
 }
    
    val state: StateFlow<StopWatchState> = serviceRepo.getTimerState().map {
        when (it) {
            is TimerState.Finished -> StopWatchState.Finished
            is TimerState.Paused -> StopWatchState.Pause(it.remainingtime)
            is TimerState.Running -> StopWatchState.Running(it.time)

        }
    }.onEach {
        state->
        Log.d("viewodel" ,state.toString())
    }
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = StopWatchState.Pause(0)

    )

    fun onPaused(){
        serviceRepo.pauseTimer()
    }
}
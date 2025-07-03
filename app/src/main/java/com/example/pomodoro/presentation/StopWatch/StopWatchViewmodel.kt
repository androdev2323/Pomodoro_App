package com.example.pomodoro.presentation.StopWatch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pomodoro.presentation.StopWatch.Data.service.PomodoroTimerService
import com.example.pomodoro.presentation.StopWatch.Domain.Model.TimerState
import com.example.pomodoro.presentation.StopWatch.Domain.Repository.TimerServiceRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class StopWatchViewmodel @Inject constructor( val serviceRepo: TimerServiceRepo,):ViewModel() {
    val state: StateFlow<StopWatchState> = serviceRepo.getTimerState().map {
        when (it) {
            is TimerState.Finished -> StopWatchState.Finished
            is TimerState.Paused -> StopWatchState.Pause(it.remainingtime)
            is TimerState.Running -> StopWatchState.Running(it.time)
            TimerState.idle -> TODO()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = StopWatchState.Pause(0)

    )
}
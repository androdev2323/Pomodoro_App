package com.example.pomodoro.presentation.StopWatch

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pomodoro.domain.repository.taskrepo
import com.example.pomodoro.presentation.StopWatch.Data.service.PomodoroTimerService
import com.example.pomodoro.presentation.StopWatch.Domain.Model.TimerState
import com.example.pomodoro.presentation.StopWatch.Domain.Repository.TimerServiceRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class StopWatchViewmodel @Inject constructor(val serviceRepo: TimerServiceRepo,val taskrepo: taskrepo) : ViewModel() {


    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<StopwatchScreenState> = taskrepo.getTaskById(9).flatMapLatest { task ->
        serviceRepo.getTimerState().map { timerState ->
            when (timerState) {
                is TimerState.Finished -> StopwatchScreenState(taskitem = task, timerState = StopWatchState.Finished)
                is TimerState.Paused -> StopwatchScreenState(taskitem = task, timerState = StopWatchState.Pause(timerState.remainingtime))
                is TimerState.Running -> StopwatchScreenState(taskitem = task, timerState = StopWatchState.Running(timerState.time))
            }
        }.onStart {

            emit(StopwatchScreenState(taskitem = task, timerState = StopWatchState.Pause((task.remaining_time * 1000) .toLong())))
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = StopwatchScreenState(taskitem = null, timerState = StopWatchState.Pause(25000))
    )
    fun onPaused() {
        serviceRepo.pauseTimer()
    }

    fun onResumed(time: Long) {
        serviceRepo.startTimer(time)
    }
}
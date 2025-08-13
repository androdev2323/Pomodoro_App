package com.example.pomodoro.presentation.StopWatch

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.pomodoro.Navigation.Stopwatch
import com.example.pomodoro.domain.repository.taskrepo
import com.example.pomodoro.presentation.StopWatch.Domain.Model.TimerState
import com.example.pomodoro.presentation.StopWatch.Domain.Repository.TimerServiceRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StopWatchViewmodel @Inject constructor(val serviceRepo: TimerServiceRepo,val taskrepo: taskrepo,private val savedStateHandle: SavedStateHandle) : ViewModel() {
private val isEnabled = MutableStateFlow(true)
    private val task:Stopwatch = savedStateHandle.toRoute()
 @OptIn(ExperimentalCoroutinesApi::class)
 val uistate:StateFlow<ViewmodelState> = isEnabled.mapLatest{

  ViewmodelState(isEnabled = it)
 }.stateIn(
     scope = viewModelScope,
     started = SharingStarted.WhileSubscribed(5000),
     initialValue = ViewmodelState()
 )


    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<StopwatchScreenState> = taskrepo.getTaskById(task.id).flatMapLatest { task ->
        serviceRepo.getTimerState().map { timerState ->
            when (timerState) {
                is TimerState.Finished -> StopwatchScreenState(taskitem = task, timerState = StopWatchState.Finished)
                is TimerState.Paused -> StopwatchScreenState(taskitem = task, timerState = StopWatchState.Pause(timerState.remainingtime))
                is TimerState.Running -> StopwatchScreenState(taskitem = task, timerState = StopWatchState.Running(timerState.time))
            }
        }.onStart {

            emit(StopwatchScreenState(taskitem = task, timerState = StopWatchState.Pause((task.remaining_time) .toLong())))
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
        serviceRepo.startTimer(time, id = task.id)
    }
    fun onFinished(){
        viewModelScope.launch {
               isEnabled.value = true

            try {
                taskrepo.updatetask(state.value.taskitem!!)
            }
            finally {
                  isEnabled.value=false
            }

        }

    }
}
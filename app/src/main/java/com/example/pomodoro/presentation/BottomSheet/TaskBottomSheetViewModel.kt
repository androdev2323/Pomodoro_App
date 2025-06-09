package com.example.pomodoro.presentation.BottomSheet

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TaskBottomSheetViewModel:ViewModel() {
private var _TaskBottomSheetState= MutableStateFlow(TaskBottomSheetState())
    val TaskBottomSheetState =_TaskBottomSheetState.asStateFlow()

    fun action(events: TaskBottomEvents) {
        when (events) {
            is TaskBottomEvents.OnDateChange -> {
                  _TaskBottomSheetState.value=_TaskBottomSheetState.value.copy(date = events.date)
            }

            is TaskBottomEvents.OnDurationChange -> {
               _TaskBottomSheetState.value=_TaskBottomSheetState.value.copy(duration = events.duration)
            }

            is TaskBottomEvents.OnTaskNameChange -> {
                 _TaskBottomSheetState.value=_TaskBottomSheetState.value.copy(taskname = events.taskname)
            }

            TaskBottomEvents.OnHideBottomSheet -> {
                _TaskBottomSheetState.value= _TaskBottomSheetState.value.copy(isSheetVisible = false)
            }
            TaskBottomEvents.OnShowBottomSheet -> {
                _TaskBottomSheetState.value= _TaskBottomSheetState.value.copy(isSheetVisible = true)
            }
        }
    }
}
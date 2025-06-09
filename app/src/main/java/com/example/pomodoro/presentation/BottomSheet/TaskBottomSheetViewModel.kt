package com.example.pomodoro.presentation.BottomSheet

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class TaskBottomSheetViewModel:ViewModel() {
private var _TaskBottomSheetState: MutableState<TaskBottomSheetState> = mutableStateOf(TaskBottomSheetState())
    val TaskBottomSheetState=_TaskBottomSheetState

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
        }
    }
}
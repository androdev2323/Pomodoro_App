package com.example.pomodoro.presentation.BottomSheet

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.pomodoro.domain.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


class TaskBottomSheetViewModel @Inject constructor() : ViewModel() {
    private var _TaskBottomSheetState = MutableStateFlow(TaskBottomSheetState())
    val TaskBottomSheetState: StateFlow<TaskBottomSheetState> = _TaskBottomSheetState.asStateFlow()

    fun action(events: TaskBottomEvents) {
        when (events) {
            is TaskBottomEvents.OnDateChange -> {
                _TaskBottomSheetState.value = _TaskBottomSheetState.value.copy(date = events.date)
            }

            is TaskBottomEvents.OnDurationChange -> {
                _TaskBottomSheetState.value =
                    _TaskBottomSheetState.value.copy(duration = events.duration)
            }

            is TaskBottomEvents.OnTaskNameChange -> {
                _TaskBottomSheetState.value =
                    _TaskBottomSheetState.value.copy(taskname = events.taskname)
            }

            TaskBottomEvents.OnHideBottomSheet -> {
                _TaskBottomSheetState.value =
                    _TaskBottomSheetState.value.copy(isSheetVisible = false)
            }

            TaskBottomEvents.OnShowBottomSheet -> {
                _TaskBottomSheetState.value =
                    _TaskBottomSheetState.value.copy(isSheetVisible = true)
            }

            TaskBottomEvents.OnSaveTask -> {
                val task: Task = Task(
                    name = _TaskBottomSheetState.value.taskname,
                    task_date = _TaskBottomSheetState.value.date,
                    duration = _TaskBottomSheetState.value.duration,
                    totatshifts = (_TaskBottomSheetState.value.duration / 0.5).toInt(),
                    )


            }
        }
    }
}


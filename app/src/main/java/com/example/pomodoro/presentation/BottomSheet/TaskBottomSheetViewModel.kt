package com.example.pomodoro.presentation.BottomSheet

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pomodoro.Data.local.Entity.Task
import com.example.pomodoro.domain.repository.taskrepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskBottomSheetViewModel @Inject constructor(val taskrepo: taskrepo) : ViewModel() {
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
                    totatshifts = (_TaskBottomSheetState.value.duration /0.5).toInt(),
                    )
                    viewModelScope.launch(Dispatchers.IO) {
                        taskrepo.insertask(task)
                    }


            }
        }
    }
}


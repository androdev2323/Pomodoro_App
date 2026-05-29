package com.example.pomodoro.presentation.BottomSheet

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pomodoro.Data.local.Entity.Task
import com.example.pomodoro.domain.repository.taskrepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskBottomSheetViewModel @Inject constructor(val taskrepo: taskrepo) : ViewModel() {
    private var _TaskBottomSheetState = MutableStateFlow(TaskBottomSheetState())
    val   TaskBottomSheetState: StateFlow<TaskBottomSheetState> = _TaskBottomSheetState.asStateFlow()

    private var taskJob: Job? = null
    

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
                    _TaskBottomSheetState.value.copy(taskName = events.taskname)
            }

            TaskBottomEvents.OnHideBottomSheet -> {
                _TaskBottomSheetState.value =
                    _TaskBottomSheetState.value.copy(isSheetVisible = false)
            }

           is  TaskBottomEvents.OnShowBottomSheet -> {
               val oldList = _TaskBottomSheetState.value.durationList
                _TaskBottomSheetState.value =
                    _TaskBottomSheetState.value.copy(id = events.id,isSheetVisible = true,taskName = events.name,duration = events.duration, durationList = oldList.filter { it >= events.duration })

            }

            TaskBottomEvents.OnSaveTask -> { 
                val task: Task = Task(
                    name = _TaskBottomSheetState.value.taskName,
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
    private fun  observeTask(){
        taskJob?.cancel()
        taskJob = viewModelScope.launch {
          taskrepo.getTaskById(TaskBottomSheetState.value.id!!).collect(){
              task->
              _TaskBottomSheetState.update {
                  val oldList = _TaskBottomSheetState.value.durationList
                  it.copy(id = task.taskid.toInt(), taskName = task.name,  duration = task.duration,durationList =  oldList.filter { it >= task.duration })
              }
          }
        }
    }
}


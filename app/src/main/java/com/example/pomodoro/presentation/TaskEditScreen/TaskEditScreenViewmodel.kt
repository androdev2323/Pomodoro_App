package com.example.pomodoro.presentation.TaskEditScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pomodoro.Data.local.Entity.Task
import com.example.pomodoro.Util.toUtcStartOfDayMillis
import com.example.pomodoro.domain.repository.taskrepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TaskEditScreenViewmodel @Inject constructor(val taskrepo: taskrepo) : ViewModel() {
    private val _date: MutableStateFlow<Long> = MutableStateFlow(0)
    private val _taskname: MutableStateFlow<TaskEditElementState<String>> =
        MutableStateFlow(TaskEditElementState(""))
    private val _duration = MutableStateFlow(2)
    private val _uiState = MutableStateFlow<Uistate>(Uistate.Idle)


    val TaskEditScreenFormState: StateFlow<TaskEditScreenFormState> =
        combine(_date, _taskname, _duration) { date, taskname, duration ->
            TaskEditScreenFormState(date, taskname, duration)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TaskEditScreenFormState()
        )

    val uiState: StateFlow<Uistate> = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Uistate.Idle
    )

    fun onDateChange(date: Long) {
        _date.value = date

    }

    fun onTasknameChange(newTaskName: String) {
        _taskname.value = _taskname.value.copy(data = newTaskName)
        _taskname.value = _taskname.value.copy(errorMessage = null)
    }

    fun onDurationChange(newDuration: Int) {
        _duration.value = newDuration
    }

    fun onResetUistate(){
        _uiState.value = Uistate.Idle
    }

    fun onSaveTask() {
        _uiState.value = Uistate.Loading

        if (validate(taskname = _taskname.value.data)) {
            val task: Task = Task(
                name = _taskname.value.data,
                task_date = _date.value,
                duration = _duration.value,
                totatshifts = (_duration.value / 0.5).toInt(),
            )
            viewModelScope.launch(Dispatchers.IO) {
                delay(1200)
                try {
                    taskrepo.insertask(task)
                    _uiState.value = Uistate.Success
                }
                catch (e:Exception){

                }

            }
        }

    }

    private fun validate(taskname: String): Boolean {
        if (taskname.trim() == "") {
            _taskname.value = _taskname.value.copy(errorMessage = "Required")
            _uiState.value = Uistate.Idle
            return false
        }
        return true
    }


}




data class TaskEditScreenFormState(
    val date: Long = LocalDate.now().toUtcStartOfDayMillis(),
    val taskname: TaskEditElementState<String> = TaskEditElementState(""),
    val duration: Int = 2,
)


sealed interface Uistate {
    object Loading : Uistate
    object Idle : Uistate
    object Success : Uistate

    data class Error(val message: String) : Uistate
}

data class TaskEditElementState<T>(
    val data:T,
    val errorMessage:String? = null
)
package com.example.pomodoro.presentation.HomeScreen

import com.example.pomodoro.Data.local.Entity.Task
import com.example.pomodoro.presentation.HomeScreen.Entity.CalendarUi

data class HomeScreenState(
    val dates: CalendarUi? = null,
    val taskList: List<Task> = emptyList(),
    val totalTaskCount: Int = 0,
    val completedTaskCount: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null,
    val sortedOrder: SortedOrder = SortedOrder.SORT_BY_RECENT,
    val sortStatus: sortDialog = sortDialog.none
)

data class UiState(
    val expandedTaskId:Long? = null
)

sealed interface sortDialog{
    data object none:sortDialog
    data object tasksort:sortDialog

}



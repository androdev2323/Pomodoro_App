package com.example.pomodoro.presentation.BottomSheet

sealed class TaskBottomEvents {
    data class OnDateChange(val date: Long) : TaskBottomEvents()
    data class OnTaskNameChange(val taskname: String) : TaskBottomEvents()
    data class OnDurationChange(val duration: Int) : TaskBottomEvents()
    object OnShowBottomSheet : TaskBottomEvents()
    object OnHideBottomSheet : TaskBottomEvents()
    object OnSaveTask:TaskBottomEvents()
}
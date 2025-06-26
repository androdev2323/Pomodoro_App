package com.example.pomodoro.presentation.BottomSheet

import com.example.pomodoro.Util.toUtcStartOfDayMillis
import java.time.LocalDate


data class TaskBottomSheetState(
    val date: Long = LocalDate.now().toUtcStartOfDayMillis(),
    val taskname: String = "",
    val duration: Int = 2,
    val isSheetVisible: Boolean = false
)
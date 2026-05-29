package com.example.pomodoro.presentation.BottomSheet

import com.example.pomodoro.Util.toUtcStartOfDayMillis
import java.time.LocalDate


data class TaskBottomSheetState(
    val id:Int? = null,
    val date: Long = LocalDate.now().toUtcStartOfDayMillis(),
    val taskName: String = "",
    val duration: Int = 2,
    val isSheetVisible: Boolean = false,
    val durationList:List<Int> = listOf(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
)
package com.example.pomodoro.presentation.BottomSheet

import java.time.LocalDate


data class TaskBottomSheetState(val date:Long = LocalDate.now().toEpochDay(),val taskname:String="",val duration:Int=2, val isSheetVisible:Boolean = false )
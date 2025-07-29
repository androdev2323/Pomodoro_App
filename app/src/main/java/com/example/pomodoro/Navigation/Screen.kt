package com.example.pomodoro.Navigation

import kotlinx.serialization.Serializable

@Serializable
data object Home

@Serializable
data class Stopwatch(val id:Int)
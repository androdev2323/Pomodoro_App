package com.example.pomodoro.presentation.StopWatch.Domain.Model

sealed class TimerState{

    data class Running(val time:Long,val id:Int):TimerState()
    data class Paused(val remainingtime:Long,val id:Int):TimerState()
    data class Finished(val id:Int):TimerState()
}

package com.example.pomodoro.presentation.StopWatch.Domain.Model

import com.example.pomodoro.Util.TimeFormatter

sealed class TimerState{
    abstract fun timeToString():String
    data class Running(val time:Long,val id:Int):TimerState(){
        override fun timeToString(): String {
          return TimeFormatter.longtoTime(time)
        }

    }
    data class Paused(val remainingtime:Long,val id:Int):TimerState(){
        override fun timeToString(): String {
            return TimeFormatter.longtoTime(remainingtime)
        }

    }
    data class Finished(val id:Int):TimerState(){
        override fun timeToString(): String {
            return TimeFormatter.longtoTime(0L)
        }

    }
}

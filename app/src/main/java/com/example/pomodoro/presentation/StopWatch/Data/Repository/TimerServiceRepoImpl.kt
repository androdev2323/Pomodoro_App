package com.example.pomodoro.presentation.StopWatch.Data.Repository

import android.content.Context
import android.content.Intent
import com.example.pomodoro.domain.repository.taskrepo
import com.example.pomodoro.presentation.StopWatch.Data.TimerStatusManager
import com.example.pomodoro.presentation.StopWatch.Domain.Model.TimerState
import com.example.pomodoro.presentation.StopWatch.Domain.Repository.TimerServiceRepo
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow



class TimerServiceRepoImpl(
    val taskrepo: taskrepo,
  @ApplicationContext  val  context: Context,
    val timerStatusManager: TimerStatusManager
):TimerServiceRepo{
    override fun getTimerState(): Flow<TimerState> {
       return timerStatusManager.timerState
    }

    override fun startTimer() {
        TODO("Not yet implemented")
    }

    override fun pauseTimer() {
      context.startForegroundService(Intent())
    }




}
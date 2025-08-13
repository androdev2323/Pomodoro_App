package com.example.pomodoro.presentation.StopWatch.Data.Repository

import android.content.Context
import android.content.Intent
import com.example.pomodoro.domain.repository.taskrepo
import com.example.pomodoro.presentation.StopWatch.Data.TimerStatusManager
import com.example.pomodoro.presentation.StopWatch.Data.service.PomodoroTimerService
import com.example.pomodoro.presentation.StopWatch.Domain.Model.TimerState
import com.example.pomodoro.presentation.StopWatch.Domain.Repository.TimerServiceRepo
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject


class TimerServiceRepoImpl @Inject constructor(
    val context: Context,
    val timerStatusManager: TimerStatusManager
) : TimerServiceRepo {
    override fun getTimerState(): Flow<TimerState> {
        return timerStatusManager.timerState.filterNotNull()

    }

    override fun startTimer(duration: Long,id:Int) {
        val intent = Intent(context, PomodoroTimerService::class.java).apply {
            this.action = PomodoroTimerService.ACTION_START_TIMER
            this.putExtra(PomodoroTimerService.REMAINING_TIME, duration)
            this.putExtra(PomodoroTimerService.EXTRA_ID, id)
        }
        context.startForegroundService(intent)
    }


    override fun pauseTimer() {
        val intent = Intent(context, PomodoroTimerService::class.java).apply {
            this.action = PomodoroTimerService.ACTION_PAUSE_TIMER

        }
        context.startForegroundService(intent)
    }


}
package com.example.pomodoro.presentation.StopWatch.Data.service

import android.content.Intent
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigator
import com.example.pomodoro.domain.repository.taskrepo
import com.example.pomodoro.presentation.StopWatch.Data.TimerStatusManager
import com.example.pomodoro.presentation.StopWatch.Domain.Model.TimerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
private  const val TICK_INTERVAL = 1000L
class PomodoroTimerService: LifecycleService(){

    companion object{
      const val ACTION_START_TIMER = "ACTION_START_TIMER"
      const val ACTION_PAUSE_TIMER = "ACTION_PAUSE_TIMER"
        const val EXTRA_ID = "extra_id"
        const val REMAINING_TIME = "remaining_time"

  }

    @Inject
    lateinit var timerstatemanager:TimerStatusManager

    private var timerjob: Job? = null
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
         when(intent?.action){
             ACTION_START_TIMER ->{

                 val id = intent.getIntExtra(EXTRA_ID,0)
                 val remaining_time = intent.getLongExtra(REMAINING_TIME,0)

                 starttimer(remaining_time,id)
             }
             ACTION_PAUSE_TIMER -> {
             pausetime()
             }
         }
        return START_STICKY
    }
    private fun starttimer(duration:Long,id:Int){
        timerjob?.cancel()
        val startime=System.currentTimeMillis()
        timerjob = lifecycleScope.launch(Dispatchers.IO){
            timerstatemanager.updatestate(TimerState.Running(duration))
            while(isActive){
                val elspasedtime = (System.currentTimeMillis() - startime).coerceAtLeast(0)
                val updatetime = duration - elspasedtime.coerceAtLeast(0)
                timerstatemanager.updatestate(TimerState.Running(updatetime))
                if(updatetime<=0){
                    break;
                }

               delay(TICK_INTERVAL)
            }
            timerstatemanager.updatestate(TimerState.Finished)

            stopSelf()

        }

    }
    private fun pausetime() {
        if (timerjob != null && timerstatemanager.timerState.value is TimerState.Running) {
                     val time = (timerstatemanager.timerState.value as  TimerState.Running) .time
               timerstatemanager.updatestate(TimerState.Paused(time))

            timerjob!!.cancel()


        }
    }


    override fun onDestroy() {

   if(timerstatemanager.timerState.value is TimerState.Running){
       timerstatemanager.updatestate(TimerState.Paused((timerstatemanager.timerState.value as TimerState.Running).time))
   }
        timerjob?.cancel()
        super.onDestroy()

    }
}
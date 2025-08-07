package com.example.pomodoro.presentation.StopWatch.Data.service

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.ServiceCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.example.pomodoro.R
import com.example.pomodoro.Util.TimeFormatter
import com.example.pomodoro.presentation.StopWatch.Data.TimerStatusManager
import com.example.pomodoro.presentation.StopWatch.Domain.Model.TimerState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TICK_INTERVAL = 1000L

@AndroidEntryPoint
class PomodoroTimerService : LifecycleService() {

    companion object {
        const val ACTION_START_TIMER = "ACTION_START_TIMER"
        const val ACTION_PAUSE_TIMER = "ACTION_PAUSE_TIMER"
        const val EXTRA_ID = "extra_id"
        const val REMAINING_TIME = "remaining_time"
        const val CHANNEL_ID = "pomodoro_timer"
        const val NOTIFICATION_ID = 12345

    }

    @Inject
    lateinit var timerstatemanager: TimerStatusManager
    override fun onCreate() {
        super.onCreate()
        getNotificationChannel(CHANNEL_ID, "Pomodoro Timer")
    }

    private var timerjob: Job? = null
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        when (intent?.action) {
            ACTION_START_TIMER -> {

                val id = intent.getIntExtra(EXTRA_ID, 0)
                val remaining_time = intent.getLongExtra(REMAINING_TIME, 0)


                starttimer(remaining_time, id)
            }

            ACTION_PAUSE_TIMER -> {
                pausetime()
            }
        }
        return START_STICKY
    }

    @SuppressLint("MissingPermission")
    private fun starttimer(duration: Long, id: Int) {
      val notidication = getServiceNotificationBuilder(title = "test" , content =duration , false)
          startForeground(NOTIFICATION_ID,notidication.build())

        timerjob?.cancel()

        timerjob = lifecycleScope.launch(Dispatchers.Default) {
            var updatetime = duration
            timerstatemanager.updatestate(TimerState.Running(updatetime))
            while (updatetime >= 0 && isActive) {

               val updatedNotifcation  = getServiceNotificationBuilder(title = "test" , content =updatetime , false)
                NotificationManagerCompat.from(this@PomodoroTimerService).notify(NOTIFICATION_ID,updatedNotifcation.build())

                timerstatemanager.updatestate(TimerState.Running(updatetime))
                delay(TICK_INTERVAL)
                updatetime -= TICK_INTERVAL


            }
            timerstatemanager.updatestate(TimerState.Finished)

            stopSelf()

        }

    }

    @SuppressLint("MissingPermission")
    private fun pausetime() {
    Log.d("pause" , "at pause")
        if (timerjob != null && timerstatemanager.timerState.value is TimerState.Running) {
            val time = (timerstatemanager.timerState.value as TimerState.Running).time
            Log.d("pause" , time.toString())
            timerstatemanager.updatestate(TimerState.Paused(time))
            val notidication = getServiceNotificationBuilder(title = "test" , content = time , true)
            startForeground(NOTIFICATION_ID,notidication.build())

            timerjob!!.cancel()


        }
    }


    override fun onDestroy() {

        if (timerstatemanager.timerState.value is TimerState.Running) {
            timerstatemanager.updatestate(TimerState.Paused((timerstatemanager.timerState.value as TimerState.Running).time))
        } else {
            timerstatemanager.updatestate(null)
        }
        timerjob?.cancel()
        super.onDestroy()

    }

    private fun getNotificationChannel(channelId: String, ChannelName: String) {


        val channel = android.app.NotificationChannel(
            channelId,
            ChannelName,
            android.app.NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager = getSystemService(android.app.NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)


    }

    private fun getServiceNotificationBuilder(
        title: String,
        content: Long,
        paused: Boolean
    ): NotificationCompat.Builder {
        val playintent = Intent(this, PomodoroTimerService::class.java).apply {
                this.action = ACTION_START_TIMER
            this.putExtra(REMAINING_TIME,content )

            }
        val playpendingIntent = PendingIntent.getForegroundService(
            this,
            0 ,
            playintent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val pauseIntent = Intent(this, PomodoroTimerService::class.java).apply {
            this.action = ACTION_PAUSE_TIMER

        }
        val pausependingIntent = PendingIntent.getForegroundService(
            this,
            0,
            pauseIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )


        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(TimeFormatter.longtoTime(content))
            .setSmallIcon(R.drawable.baseline_alarm_24)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(
               if (paused) R.drawable.baseline_play_arrow_24 else R.drawable.ic_pause,
               if (paused) "Resume" else "Pause",
                 if(paused) playpendingIntent else pausependingIntent
            )
            .setOnlyAlertOnce(true)



    }
}
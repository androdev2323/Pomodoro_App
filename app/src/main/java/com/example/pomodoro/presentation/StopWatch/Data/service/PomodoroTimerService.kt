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
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.example.pomodoro.MainActivity
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
    private var id:Int = -1
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        when (intent?.action) {
            ACTION_START_TIMER -> {

               id = intent.getIntExtra(EXTRA_ID, 0)
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
      val notidication = getServiceNotificationBuilder(id =id,title = "test" , content =duration , notificationStatus = NotificationStatus.Playing)
           startForeground(NOTIFICATION_ID,notidication.build())

        timerjob?.cancel()

        timerjob = lifecycleScope.launch(Dispatchers.Default) {
            var updatetime = duration
            timerstatemanager.updatestate(TimerState.Running(updatetime))
            while (updatetime >= 0 && isActive) {

               val updatedNotifcation  = getServiceNotificationBuilder(id = id, title = "test" , content =updatetime , notificationStatus = NotificationStatus.Playing)
                NotificationManagerCompat.from(this@PomodoroTimerService).notify(NOTIFICATION_ID,updatedNotifcation.build())

                timerstatemanager.updatestate(TimerState.Running(updatetime))
                delay(TICK_INTERVAL)
                updatetime -= TICK_INTERVAL


            }
            timerstatemanager.updatestate(TimerState.Finished)
            val updatedNotifcation  = getServiceNotificationBuilder(id = id, title = "test" , content =updatetime , notificationStatus = NotificationStatus.Finished)
            NotificationManagerCompat.from(this@PomodoroTimerService).notify(NOTIFICATION_ID,updatedNotifcation.build())


        }

    }

    @SuppressLint("MissingPermission")
    private fun pausetime() {
    Log.d("pause" , "at pause")
        if (timerjob != null && timerstatemanager.timerState.value is TimerState.Running) {
            val time = (timerstatemanager.timerState.value as TimerState.Running).time
            Log.d("pause" , time.toString())
            timerstatemanager.updatestate(TimerState.Paused(time))
            val notidication = getServiceNotificationBuilder(id =id,title = "test" , content = time , notificationStatus = NotificationStatus.Paused)
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
        id:Int,
        title: String,
        content: Long,
       notificationStatus: NotificationStatus
    ): NotificationCompat.Builder {

        val contentIntent = Intent(this, MainActivity::class.java).apply{
            data ="pomodoroapp://stopwatch/${id}".toUri()
        }
        val contentpedningintent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(contentIntent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        }
        val playintent = Intent(this, PomodoroTimerService::class.java).apply {
            this.action = ACTION_START_TIMER
            this.putExtra(REMAINING_TIME, content)
            this.putExtra(EXTRA_ID, id)

        }
        val playpendingIntent = PendingIntent.getForegroundService(
            this,
            0,
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


        val build = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(TimeFormatter.longtoTime(content))
            .setSmallIcon(R.drawable.baseline_alarm_24)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(contentpedningintent)



        if (notificationStatus != NotificationStatus.Finished) {
              build .setOnlyAlertOnce(true)
            if (notificationStatus.equals(NotificationStatus.Playing)) {
                build.addAction(
                R.drawable.ic_pause,
                    "Pause",
                    pausependingIntent
                )
            } else {
                build.addAction(
                    R.drawable.baseline_play_arrow_24,
                    "Resume",
                    playpendingIntent
                )

            }
        }
        else{
            build.addAction(
               R.drawable.baseline_stop_24,
                "Dismiss",
                contentpedningintent

            )
        }
        return build
    }
}
enum class NotificationStatus{
    Playing,
    Paused,
    Finished
}
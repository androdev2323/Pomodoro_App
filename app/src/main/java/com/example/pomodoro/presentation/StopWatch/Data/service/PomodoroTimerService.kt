package com.example.pomodoro.presentation.StopWatch.Data.service

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.IBinder
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
import com.example.pomodoro.presentation.AppBlock.data.AppBlockService
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
    private lateinit var AppBlockServices: AppBlockService
    private var mbinder = false
    private val connection =
        object : ServiceConnection {
            override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
                val binder = p1 as AppBlockService.LocalBinder
                AppBlockServices = binder.getService()

                mbinder = true;
            }

            override fun onServiceDisconnected(p0: ComponentName?) {
                Log.d("dc", "reached and reconnected")
                if (mbinder) {
                    startService(Intent(this@PomodoroTimerService, AppBlockService::class.java))

                }
            }

        }


    companion object {
        const val ACTION_START_TIMER = "ACTION_START_TIMER"
        const val ACTION_PAUSE_TIMER = "ACTION_PAUSE_TIMER"
        const val ACTION_FINISHED = "ACTION_FINISHED"
        const val ACTION_STOPALARM = "ACTION_STOPALARM"
        const val EXTRA_ID = "extra_id"
        const val REMAINING_TIME = "remaining_time"
        const val CHANNEL_ID = "pomodoro_timer"
        const val IS_WORK = "ISWORK"
        const val NOTIFICATION_ID = 12345

    }

    @Inject
    lateinit var timerstatemanager: TimerStatusManager
    override fun onCreate() {
        super.onCreate()
        Intent(this, AppBlockService::class.java).also {
            bindService(
                Intent(this@PomodoroTimerService, AppBlockService::class.java),
                connection,
                Context.BIND_AUTO_CREATE
            )
        }
        getNotificationChannel(CHANNEL_ID, "Pomodoro Timer")


    }

    private var alarmringtone: Ringtone? = null
    private var timerjob: Job? = null
    private var id: Int = -1
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        when (intent?.action) {
            ACTION_START_TIMER -> {

                id = intent.getIntExtra(EXTRA_ID, 0)
                val remaining_time = intent.getLongExtra(REMAINING_TIME, 0)
                val isWork = intent.getBooleanExtra(IS_WORK, false)


                starttimer(remaining_time, id, isWork)
            }

            ACTION_PAUSE_TIMER -> {
                pausetime()
            }

            ACTION_FINISHED -> {
                onFinish()
            }

            ACTION_STOPALARM -> {
                stopAlarm()
            }
        }
        return START_STICKY
    }

    @SuppressLint("MissingPermission")
    private fun starttimer(duration: Long, id: Int, isWork: Boolean) {
        val notidication = getServiceNotificationBuilder(
            id = id,
            title = "test",
            content = duration,
            notificationStatus = NotificationStatus.Playing
        )
        if (isWork) {
            mbinder = true
            startService(
                Intent(
                    this@PomodoroTimerService,
                    AppBlockService::class.java
                )
            )
        } else {
            mbinder = false
            stopService(Intent(this@PomodoroTimerService, AppBlockService::class.java))

        }
        startForeground(NOTIFICATION_ID, notidication.build())

        timerjob?.cancel()

        timerjob = lifecycleScope.launch(Dispatchers.Default) {
            var updatetime = duration
            timerstatemanager.updatestate(TimerState.Running(updatetime, id))
            while (updatetime >= 0 && isActive) {

                val updatedNotifcation = getServiceNotificationBuilder(
                    id = id,
                    title = "test",
                    content = updatetime,
                    notificationStatus = NotificationStatus.Playing
                )
                NotificationManagerCompat.from(this@PomodoroTimerService)
                    .notify(NOTIFICATION_ID, updatedNotifcation.build())

                timerstatemanager.updatestate(TimerState.Running(updatetime, id))
                delay(TICK_INTERVAL)
                updatetime -= TICK_INTERVAL


            }
            startAlarm()
            timerstatemanager.updatestate(TimerState.Finished(id))
            val updatedNotifcation = getServiceNotificationBuilder(
                id = id,
                title = "test",
                content = updatetime,
                notificationStatus = NotificationStatus.Finished
            )
            NotificationManagerCompat.from(this@PomodoroTimerService)
                .notify(NOTIFICATION_ID, updatedNotifcation.build())


        }

    }

    @SuppressLint("MissingPermission")
    private fun pausetime() {
        Log.d("pause", "at pause")
        if (timerjob != null && timerstatemanager.timerState.value is TimerState.Running) {
            val time = (timerstatemanager.timerState.value as TimerState.Running).time
            Log.d("pause", time.toString())
            timerstatemanager.updatestate(TimerState.Paused(time, id))
            val notidication = getServiceNotificationBuilder(
                id = id,
                title = "test",
                content = time,
                notificationStatus = NotificationStatus.Paused
            )
            startForeground(NOTIFICATION_ID, notidication.build())


            timerjob!!.cancel()


        }
    }

    private fun onFinish() {
        timerjob?.cancel()
        timerstatemanager.updatestate(null)
        ServiceCompat.stopForeground(this, ServiceCompat.STOP_FOREGROUND_REMOVE)
    }

    private fun stopAlarm() {
        alarmringtone?.takeIf { it.isPlaying }?.stop()
        alarmringtone = null
        NotificationManagerCompat.from(this).cancel(NOTIFICATION_ID)
    }

    private fun startAlarm() {
        stopAlarm()
        try {
            val alarmuri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            alarmringtone = RingtoneManager.getRingtone(this, alarmuri)
            alarmringtone?.let { ringtone ->
                ringtone.audioAttributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()
                ringtone.isLooping = true
                ringtone.play()
            }

        } catch (e: Exception) {
            Log.d("error", e.message.toString())
        }
    }


    override fun onDestroy() {
        stopAlarm()
        unbindService(connection)
        if (timerstatemanager.timerState.value is TimerState.Running) {
            timerstatemanager.updatestate(
                TimerState.Paused(
                    (timerstatemanager.timerState.value as TimerState.Running).time,
                    id = id
                )
            )
        } else {
            timerstatemanager.updatestate(null)
        }
        timerjob?.cancel()
        super.onDestroy()

    }

    private fun getNotificationChannel(channelId: String, ChannelName: String) {


        val channel = NotificationChannel(
            channelId,
            ChannelName,
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)


    }

    private fun getServiceNotificationBuilder(
        id: Int,
        title: String,
        content: Long,
        notificationStatus: NotificationStatus
    ): NotificationCompat.Builder {

        val contentIntent = Intent(this, MainActivity::class.java).apply {
            data = "pomodoroapp://stopwatch/${id}".toUri()
        }
        val contentpedningintent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(contentIntent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        }

        val alarmIntent = Intent(this, PomodoroTimerService::class.java).apply {
            this.action = ACTION_STOPALARM

        }
        val alarmpendingIntent = PendingIntent.getService(
            this,
            0,
            alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
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
            build.setOnlyAlertOnce(true)
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
        } else {
            build.addAction(
                R.drawable.baseline_stop_24,
                "Dismiss",
                alarmpendingIntent

            )
                .setSilent(true)
        }
        return build
    }
}

enum class NotificationStatus {
    Playing,
    Paused,
    Finished
}
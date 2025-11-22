package com.example.pomodoro.presentation.AppBlock.data

import android.app.Service
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.example.pomodoro.presentation.AppBlock.Components.ServiceSafeBlockedSheet
import com.example.pomodoro.presentation.AppBlock.Domain.Repository.InstalledPackageRepo
import com.example.pomodoro.presentation.AppBlock.data.local.Entity.AndroidPackage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.serializer
import javax.inject.Inject

val TAG ="AppBlockService"
@AndroidEntryPoint
class AppBlockService: Service(), LifecycleOwner, SavedStateRegistryOwner {

    private lateinit var windowManager: WindowManager
    private var overlayView: View? = null
    private var isShown = false


    @Inject
    lateinit var installedPackageRepo: InstalledPackageRepo
    private val lifecycleRegistry = LifecycleRegistry(this)
    private lateinit var savedStateRegistryController: SavedStateRegistryController
    override val lifecycle: Lifecycle get() = lifecycleRegistry
    override val savedStateRegistry: SavedStateRegistry get() = savedStateRegistryController.savedStateRegistry


    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.IO + serviceJob)
    private var pollingJob: Job? = null
   private var BlockSet = emptySet<String>()
    private val binder= LocalBinder()
    inner class LocalBinder: Binder(){
        fun getService():AppBlockService =  this@AppBlockService
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onCreate() {
        super.onCreate()


        savedStateRegistryController = SavedStateRegistryController.create(this)
        savedStateRegistryController.performRestore(null)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        Log.d(TAG, "onCreate: Service created.")
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        serviceScope.launch {
            installedPackageRepo.getAllApps().collect {
                BlockSet = it.filter { it.isenabled== true }.map { it.packageName }.toSet()
            }
        }

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: Received start command.")

        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        startPollingLoop()

        return START_STICKY
    }

   fun startPollingLoop() {
       if (pollingJob?.isActive == true) {
           return
       }
       pollingJob = serviceScope.launch {
           while (isActive) {
               checkUsageStatsAndOverlay(blockset = BlockSet)
               delay(1500L)
           }
       }
    }

    private fun checkUsageStatsAndOverlay(blockset: Set<String> ) {

        val foregroundApp = getForegroundApp(this)


        if ((foregroundApp in blockset)  && !isShown) {
            showOverlay()
        } else if ((foregroundApp !in blockset) && isShown) {
            removeOverlay()
        }
    }
     fun stopUsageStatsCheck(){
         pollingJob?.cancel()
         pollingJob = null
         if(isShown){
             removeOverlay()
         }
    }

    private fun showOverlay() {
        serviceScope.launch {
            withContext(Dispatchers.Main) {
                if (overlayView != null) return@withContext

                val composeView = ComposeView(this@AppBlockService).apply {
                    setViewTreeLifecycleOwner(this@AppBlockService)
                    setViewTreeSavedStateRegistryOwner(this@AppBlockService)

                    setContent {

                        ServiceSafeBlockedSheet(onSheetClosed = {
                            handleSheetClose()
                        })
                    }
                }
                overlayView = composeView

                try {
                    windowManager.addView(overlayView, getLayoutParams())
                    isShown = true

                } catch (e: Exception) {
                    Log.e(TAG, "Error adding overlay view", e)
                }
            }
        }
    }

    private fun removeOverlay() {
        serviceScope.launch {
            withContext(Dispatchers.Main) {
                if (overlayView == null) return@withContext
                try {
                    windowManager.removeView(overlayView)
                    overlayView = null
                    isShown = false
                    Log.d(TAG, "Overlay removed.")
                } catch (e: Exception) {
                    Log.e(TAG, "Error removing overlay view", e)
                }
            }
        }
    }


    private fun handleSheetClose() {
        serviceScope.launch {
            sendToHomeScreen()
            delay(300L)
            removeOverlay()
        }
    }

    private fun getForegroundApp(context: Context): String? {
        val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val endTime = System.currentTimeMillis()
        val beginTime = endTime - 7000
        val usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, beginTime, endTime)
        return usageStatsList?.maxByOrNull { it.lastTimeUsed }?.packageName
    }

    private fun getLayoutParams(): WindowManager.LayoutParams {
        val type =
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        return WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            type,
            0,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.CENTER
        }
    }

    private fun sendToHomeScreen() {
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "onDestroy: SERVICE WAS DESTROYED.")
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        serviceJob.cancel()
        removeOverlay()
    }


}
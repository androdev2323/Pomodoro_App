package com.example.pomodoro.domain.repository

import android.os.NetworkOnMainThreadException
import com.example.pomodoro.Data.local.Entity.Task
import com.example.pomodoro.Util.NetworkResult
import kotlinx.coroutines.flow.Flow

interface taskrepo {

    fun gettaskbydate(date:Long): Flow<NetworkResult<List<Task>>>
    fun getTaskById(id:Int): Flow<Task>
    suspend fun insertask(task: Task)
    suspend fun updatetask(
       task: Task
    )
   suspend fun updateremainingtime(
      task: Task, remaining_time: Int
    )
    suspend fun deletetask(task: Task)
}
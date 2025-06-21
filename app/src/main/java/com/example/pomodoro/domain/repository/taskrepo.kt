package com.example.pomodoro.domain.repository

import com.example.pomodoro.Data.local.Entity.Task
import com.example.pomodoro.Util.NetworkResult
import kotlinx.coroutines.flow.Flow

interface taskrepo {

    fun gettaskbydate(date:Long): Flow<NetworkResult<List<Task>>>
    suspend fun insertask(task: Task)
    suspend fun updatetask(
        id: Int,
        completedShifts: Int,
        session_status: String,
        session_type: String,
        remaining_time: Int
    )
   suspend fun updateremainingtime(
        id: Int, remaining_time: Int
    )
}
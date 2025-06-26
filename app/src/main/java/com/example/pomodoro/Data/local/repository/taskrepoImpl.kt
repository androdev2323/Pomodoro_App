package com.example.pomodoro.Data.local.repository

import android.util.Log
import com.example.pomodoro.Data.local.Dao.TaskDao
import com.example.pomodoro.Data.local.Entity.Task
import com.example.pomodoro.Util.NetworkResult
import com.example.pomodoro.domain.repository.taskrepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class taskrepoImpl(val taskDao: TaskDao):taskrepo {
    override fun gettaskbydate(date: Long): Flow<NetworkResult<List<Task>>>{
          return taskDao.getaskbydate(date).map { it->
              NetworkResult.Success(it)
          }.catch {
              NetworkResult.Error<List<Task>>(it.message.toString())
          }

    }

    override fun getalltask(): Flow<List<Task>> {
        val list = taskDao.getalltask()

        return flow {
            emit(list)
        }
    }

    override suspend fun insertask(task: Task) {
               taskDao.insertask(task)
    }

    override suspend fun updatetask(
        id: Int,
        completedShifts: Int,
        session_status: String,
        session_type: String,
        remaining_time: Int
    ) {
              taskDao.updatetask(id,completedShifts,session_status, session_type, remaining_time)
    }

    override suspend fun updateremainingtime(id: Int, remaining_time: Int) {
        taskDao.updateremainingtime(id, remaining_time)
    }
}
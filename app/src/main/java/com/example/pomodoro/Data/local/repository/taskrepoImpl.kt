package com.example.pomodoro.Data.local.repository

import android.util.Log
import com.example.pomodoro.Data.PomdoroContract
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

    override fun getTaskById(id: Int): Flow<Task> {
      return taskDao.getTaskById(id)


    }

    override suspend fun insertask(task: Task) {
               taskDao.insertask(task)
    }

    override suspend fun updatetask(
        task:Task
    ) {
        var completedshift = task.completedshifts
        var currentsessiontype = task.session_type
        var currentsessionstatus = task.session_status
        var remaining_time = task.remaining_time
        if (task.session_type.equals(PomdoroContract.POMODORO_WORK)) {
            completedshift++;
        }
       if(completedshift == task.totatshifts){
           currentsessionstatus = PomdoroContract.STATUS_COMPLETED

       }
        if (task.session_type.equals(PomdoroContract.POMODORO_WORK)) {
            if(completedshift % PomdoroContract.LONGBREAK_SESSIONS == 0){
                currentsessiontype = PomdoroContract.POMODORO_lONGBREAK
                remaining_time = PomdoroContract.DEFAULT_LONGBREAKDURATION

            }else{
                currentsessiontype = PomdoroContract.POMODORO_SHORTBREAK
                remaining_time = PomdoroContract.DEFAULT_SHORTBREAKDURATION
            }
        }
        else{
            currentsessiontype = PomdoroContract.POMODORO_WORK
            remaining_time = PomdoroContract.DEFAULT_WORKDURATION
        }
        taskDao.updatetask(
            task.taskid.toInt(),
            completedshift,
            currentsessionstatus,
            currentsessiontype,
            remaining_time
        )

    }

    override suspend fun updateremainingtime(task:Task, remaining_time: Int) {

    }

    override suspend fun deletetask(task: Task) {
        taskDao.deletetask(task)

    }
}
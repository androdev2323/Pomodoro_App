package com.example.pomodoro.Data.local.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.pomodoro.Data.local.Entity.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("Select * from Task WHERE task_date  =:date")
    fun getaskbydate(date: Long): Flow<List<Task>>


    @Query("Select * from Task where taskid =:id ")
    fun getTaskById(id: Int): Flow<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertask(task: Task)

    @Query(
        "UPDATE Task SET completedshifts=:completedShifts," +
                "session_status=:session_status," +
                "session_type=:session_type," +
                "remaining_time=:remaining_time WHERE taskid=:id"
    )
   suspend fun updatetask(
        id: Int,
        completedShifts: Int,
        session_status: String,
        session_type: String,
        remaining_time: Int
    )

    @Query("UPDATE Task SET remaining_time=:remaining_time WHERE taskid=:id")
  suspend  fun updateremainingtime(
        id: Int, remaining_time: Int
    )


}
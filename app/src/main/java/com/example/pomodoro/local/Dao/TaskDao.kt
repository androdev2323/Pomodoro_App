package com.example.pomodoro.local.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.pomodoro.domain.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
 @Query("Select * from Task t where t.task_date == :date")
 fun getaskbydate(date:Long): Flow<List<Task>>

 @Insert(onConflict= OnConflictStrategy.REPLACE)
 fun insertask(task:Task)




}
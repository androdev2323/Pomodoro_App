package com.example.pomodoro.local.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pomodoro.domain.model.Session

@Dao
interface SessionDao {
@Insert(onConflict = OnConflictStrategy.REPLACE)
fun createsession(session: Session)


@Query("Select * from session s where s.taskid==:taskid")
fun getsession(taskid:Int):Session
}
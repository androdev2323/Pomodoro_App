package com.example.pomodoro.Data.local.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.pomodoro.Data.PomdoroContract

import java.time.LocalDate


@Entity(tableName = "Task")
 data class Task(
 @PrimaryKey(autoGenerate = true)
 val taskid:Long=0L,
 val name:String,
 val task_date:Long ,
 val duration:Int,
 val totatshifts:Int,
  val completedshifts:Int=0,
  val session_status:String = PomdoroContract.STATUS_PENDING,
  val session_type:String = PomdoroContract.POMODORO_SHORTBREAK,
  val remaining_time:Int = PomdoroContract.DEFAULT_SHORTBREAKDURATION

 )
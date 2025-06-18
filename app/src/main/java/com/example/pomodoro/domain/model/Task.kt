package com.example.pomodoro.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.pomodoro.local.TypeConvertors.DateTypeConvertor


@Entity()
@TypeConverters(DateTypeConvertor::class)
 data class Task(
 @PrimaryKey(autoGenerate = true)
 val taskid:Long=0L,
 val name:String,
 val task_date:Long,
 val duration:Int,
 val totatshifts:Int,

 )
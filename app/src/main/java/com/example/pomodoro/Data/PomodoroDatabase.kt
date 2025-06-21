package com.example.pomodoro.Data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pomodoro.Data.local.Dao.TaskDao
import com.example.pomodoro.Data.local.Entity.Task
import com.example.pomodoro.Data.local.TypeConvertors.DateTypeConvertor


@Database(entities = [Task::class], version = 1)
@TypeConverters(DateTypeConvertor::class)
abstract class PomodoroDatabase: RoomDatabase() {
    abstract fun TaskDao():TaskDao
}
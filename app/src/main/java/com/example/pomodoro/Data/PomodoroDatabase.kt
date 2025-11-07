package com.example.pomodoro.Data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pomodoro.Data.local.Dao.TaskDao
import com.example.pomodoro.Data.local.Entity.Task
import com.example.pomodoro.presentation.AppBlock.data.local.Entity.AndroidPackage
import com.example.pomodoro.presentation.AppBlock.data.local.Entity.InstalledPackage
import com.example.pomodoro.presentation.AppBlock.data.local.dao.InstalledPackageDao


@Database(entities = [Task::class,AndroidPackage::class], version = 1)
abstract class PomodoroDatabase: RoomDatabase() {
    abstract fun TaskDao():TaskDao
    abstract fun InstalledPackageDao():InstalledPackageDao
}
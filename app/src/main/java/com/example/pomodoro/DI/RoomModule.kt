package com.example.pomodoro.DI

import android.content.Context
import androidx.room.Room
import com.example.pomodoro.Data.PomodoroDatabase
import com.example.pomodoro.Data.local.Dao.TaskDao
import com.example.pomodoro.presentation.AppBlock.data.local.dao.InstalledPackageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun ProvidesPomodoroDatabase(@ApplicationContext applicationContext: Context): PomodoroDatabase {
        return Room.databaseBuilder(
            applicationContext,
            PomodoroDatabase::class.java,
            "pomodoro_database"
        ).build()
    }

    @Provides
    @Singleton
    fun ProvidesTaskDao(pomodoroDatabase: PomodoroDatabase): TaskDao {
      return pomodoroDatabase.TaskDao()
    }

    @Provides
    @Singleton
    fun ProvidesInstalledPackageDao(pomodoroDatabase: PomodoroDatabase): InstalledPackageDao{
        return  pomodoroDatabase.InstalledPackageDao()
    }

}
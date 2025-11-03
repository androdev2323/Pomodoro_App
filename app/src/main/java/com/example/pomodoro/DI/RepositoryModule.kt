package com.example.pomodoro.DI

import android.content.Context
import com.example.pomodoro.Data.local.Dao.TaskDao
import com.example.pomodoro.domain.repository.taskrepo
import com.example.pomodoro.Data.local.repository.taskrepoImpl
import com.example.pomodoro.presentation.AppBlock.Domain.Repository.InstalledPackageRepo
import com.example.pomodoro.presentation.AppBlock.Domain.Repository.PackageInfoDataSourceRepo
import com.example.pomodoro.presentation.AppBlock.data.InstalledAppsDataSource
import com.example.pomodoro.presentation.AppBlock.data.Repository.InstalledPackageRepoImpl
import com.example.pomodoro.presentation.AppBlock.data.Repository.PackageInfoDataSourceRepoImpl
import com.example.pomodoro.presentation.AppBlock.data.local.dao.InstalledPackageDao
import com.example.pomodoro.presentation.HomeScreen.Entity.CalendarDataSource
import com.example.pomodoro.presentation.HomeScreen.Entity.CalendarRepoImpl
import com.example.pomodoro.presentation.HomeScreen.Repository.CalednarRepo
import com.example.pomodoro.presentation.StopWatch.Data.Repository.TimerServiceRepoImpl
import com.example.pomodoro.presentation.StopWatch.Data.TimerStatusManager
import com.example.pomodoro.presentation.StopWatch.Domain.Repository.TimerServiceRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideCalendarDataSource(): CalendarDataSource {
        return CalendarDataSource()
    }

    @Provides
    @Singleton
    fun provideCalendarRepo(calendarDataSource: CalendarDataSource): CalednarRepo {
        return CalendarRepoImpl(calendarDataSource)
    }

    @Provides
    @Singleton
    fun providesTaskRepo(taskDao: TaskDao): taskrepo {
        return taskrepoImpl(taskDao)
    }

    @Provides
    @Singleton
    fun ProvidesTimerServiceRepo(@ApplicationContext context: Context, timerStatusManager: TimerStatusManager): TimerServiceRepo {
        return TimerServiceRepoImpl(context = context, timerStatusManager = timerStatusManager)
    }

    @Provides
    @Singleton
    fun providesInstalledAppsDataSource(@ApplicationContext context: Context):InstalledAppsDataSource{
        return InstalledAppsDataSource(context)

    }
    
    @Provides
    @Singleton
    fun providesInstalledAppsRepo(installedAppsDataSource: InstalledAppsDataSource): PackageInfoDataSourceRepo{
        return PackageInfoDataSourceRepoImpl(installedAppsDataSource)
    }

    @Provides
    @Singleton
    fun providesInstalledPackageRepo(installedPackageDao: InstalledPackageDao): InstalledPackageRepo {
        return  InstalledPackageRepoImpl(installedPackageDao)
    }
}
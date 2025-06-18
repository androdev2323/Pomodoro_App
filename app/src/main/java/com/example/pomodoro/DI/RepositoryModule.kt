package com.example.pomodoro.DI

import com.example.pomodoro.presentation.HomeScreen.Entity.CalendarDataSource
import com.example.pomodoro.presentation.HomeScreen.Entity.CalendarRepoImpl
import com.example.pomodoro.presentation.HomeScreen.Repository.CalednarRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideCalendarRepo(): CalednarRepo {
        return CalendarRepoImpl(CalendarDataSource());
    }
}
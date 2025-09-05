package com.example.pomodoro.presentation.HomeScreen.Repository

import com.example.pomodoro.Util.NetworkResult
import com.example.pomodoro.presentation.HomeScreen.Entity.CalendarDataSource
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface CalednarRepo {
    suspend fun getinbetweenDates(startdate:LocalDate = LocalDate.now(),lastselectdate: LocalDate): Flow <NetworkResult<List<LocalDate>>>
}
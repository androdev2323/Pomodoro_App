package com.example.pomodoro.presentation.HomeScreen.Entity

import com.example.pomodoro.Util.NetworkResult
import com.example.pomodoro.presentation.HomeScreen.Repository.CalednarRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate

class CalendarRepoImpl(val CalendarDataSource: CalendarDataSource): CalednarRepo {
    override suspend fun getinbetweenDates(startdate: LocalDate, lastselectdate: LocalDate): Flow<NetworkResult<List<LocalDate>>> {
         return flow {

         val result=   CalendarDataSource.getDate(startdate, lastselectedate = lastselectdate)
emit(NetworkResult.Success(result))
         }
    }

}
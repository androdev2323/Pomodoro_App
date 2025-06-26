package com.example.pomodoro.presentation.HomeScreen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pomodoro.Data.local.Entity.Task
import com.example.pomodoro.Util.NetworkResult
import com.example.pomodoro.Util.toUtcStartOfDayMillis
import com.example.pomodoro.domain.repository.taskrepo
import com.example.pomodoro.presentation.HomeScreen.Entity.CalendarUi
import com.example.pomodoro.presentation.HomeScreen.Repository.CalednarRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewmodel @Inject constructor(val repo: CalednarRepo, val repository: taskrepo) :
    ViewModel() {
    private val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private var _HomescreenState: MutableStateFlow<HomeScreenState> =
        MutableStateFlow(HomeScreenState())
    val HomescreenState = _HomescreenState.asStateFlow()

    init {
        action(events = HomeScreenEvents.getDates(lastselectedDate = LocalDate.now()))
        action(events = HomeScreenEvents.GetTasks(date = LocalDate.now()))

    }

    fun action(events: HomeScreenEvents) {

        when (events) {
            is HomeScreenEvents.getDates -> {
                viewModelScope.launch() {
                    repo.getinbetweenDates(
                        startdate = events.startdate,
                        lastselectdate = events.lastselectedDate
                    ).collectLatest {
                        when (it) {
                            is NetworkResult.Error -> {}
                            is NetworkResult.Success -> {
                                val result = it.data
                                _HomescreenState.update { state ->
                                    state.copy(
                                        dates = CalendarUi(
                                            selecteddate = toDate(
                                                events.lastselectedDate,
                                                true
                                            ), visbledates = result.map {
                                                toDate(it, it.equals(events.lastselectedDate))
                                            })
                                    )
                                }
                            }


                        }
                    }

                }
            }

            is HomeScreenEvents.GetTasks -> {
                viewModelScope.launch(Dispatchers.IO) {


                        repository.gettaskbydate(events.date.toUtcStartOfDayMillis()).collect {
                                result->
                            when(result){
                                is NetworkResult.Error -> TODO()
                                is NetworkResult.Success-> {

                                    _HomescreenState.update {
                                        it.copy(taskList = result.data)
                                    }
                                }
                            }










                        }

                }
            }


            else -> {}
        }


    }

    private fun toDate(date: LocalDate, isSelected: Boolean) = CalendarUi.Date(
        date = date,
        isSelected = isSelected,
        isToday = date.equals(LocalDate.now())
    )
}



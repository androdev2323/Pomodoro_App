package com.example.pomodoro.presentation.HomeScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pomodoro.Util.NetworkResult
import com.example.pomodoro.presentation.HomeScreen.Entity.CalendarUi
import com.example.pomodoro.presentation.HomeScreen.Repository.CalednarRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class HomeScreenViewmodel(val repo: CalednarRepo) : ViewModel() {
    private var _HomescreenState: MutableStateFlow<HomeScreenState> =
        MutableStateFlow(HomeScreenState.Loadng)
    val HomescreenState = _HomescreenState.asStateFlow()
    fun action(events: HomeScreenEvents) {

        when (events) {
            is HomeScreenEvents.getDates -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repo.getinbetweenDates(
                        startdate = events.startdate,
                        lastselectdate = events.lastselectedDate
                    ).collectLatest {
                        when (it) {
                            is NetworkResult.Error -> {}
                            is NetworkResult.Success -> {
                                val result = it.data

                                _HomescreenState.update {
                                    HomeScreenState.Success(
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
        }


    }

    private fun toDate(date: LocalDate, isSelected: Boolean) = CalendarUi.Date(
        date = date,
        isSelected = isSelected,
        isToday = date.equals(LocalDate.now())
    )

}

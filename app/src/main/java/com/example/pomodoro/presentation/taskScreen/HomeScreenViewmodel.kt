package com.example.pomodoro.presentation.HomeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pomodoro.Data.local.Entity.Task
import com.example.pomodoro.Util.NetworkResult
import com.example.pomodoro.Util.toUtcStartOfDayMillis
import com.example.pomodoro.domain.repository.taskrepo
import com.example.pomodoro.presentation.HomeScreen.Entity.CalendarUi
import com.example.pomodoro.presentation.HomeScreen.Repository.CalednarRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.lang.Thread.State
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewmodel @Inject constructor(
    private val calendarRepo: CalednarRepo,
    private val taskRepository: taskrepo
) : ViewModel() {


    private val _sortingOrder = MutableStateFlow(SortedOrder.SORT_BY_RECENT)
    private val _expandedid = MutableStateFlow<Long?>(null)



    private val _selectedDate = MutableStateFlow(LocalDate.now())
     private val _sortdialogstatus:MutableStateFlow<sortDialog> = MutableStateFlow(sortDialog.none)



    @OptIn(ExperimentalCoroutinesApi::class)
    private val calendarUiFlow = _selectedDate.flatMapLatest { selectedDate ->

        val pageStartDate = getStartOfWeek(selectedDate)
        calendarRepo.getinbetweenDates(startdate = pageStartDate, lastselectdate = selectedDate)
    }

    private val tasksFlow = _selectedDate.flatMapLatest { date ->
        taskRepository.gettaskbydate(date.toUtcStartOfDayMillis())
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    val homeScreenUiState:StateFlow<UiState> = _expandedid.mapLatest{
        UiState(expandedTaskId = it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UiState()
    )

    val homescreenState: StateFlow<HomeScreenState> = combine(
        calendarUiFlow,
        tasksFlow,
        _sortingOrder,
        _selectedDate,
        _sortdialogstatus
    ) { calendarResult, taskResult, order, currentDate,dialogstatus ->

        val calendarUi = if (calendarResult is NetworkResult.Success) {
            val dates = calendarResult.data ?: emptyList()
            CalendarUi(
                selecteddate = toDate(currentDate, true),
                visbledates = dates.map { toDate(it, it.isEqual(currentDate)) }
            )
        } else {
            CalendarUi(selecteddate = toDate(currentDate, true), visbledates = emptyList())
        }

        if (taskResult is NetworkResult.Success) {
            val unsortedList = taskResult.data
            val sortedList = when (order) {
                SortedOrder.SORT_BY_RECENT -> unsortedList
                SortedOrder.SORT_BY_DURATION -> unsortedList.sortedByDescending { it.duration }
                SortedOrder.SORT_BY_NAME -> unsortedList.sortedBy { it.name }
            }


            HomeScreenState(
                dates = calendarUi,
                taskList = sortedList,
                totalTaskCount = unsortedList.size,
                sortStatus =dialogstatus ,
                sortedOrder = order,
                completedTaskCount = unsortedList.count { it.completedshifts == it.totatshifts }
            )
        } else {
            HomeScreenState(dates = calendarUi, isLoading = true, sortStatus = dialogstatus, sortedOrder = order)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeScreenState(isLoading = true, sortedOrder = SortedOrder.SORT_BY_RECENT)
    )

       fun onSortDialogStatusChanged(newStatus: sortDialog) {
        _sortdialogstatus.value = newStatus
       }

    fun onDateClicked(date: LocalDate) {

        _selectedDate.value = date
    }

    fun onSortOrderChanged(newOrder: SortedOrder) {
        _sortingOrder.value = newOrder
    }

    fun onSortDialogDismissed(sortedOrder: SortedOrder){

      onSortOrderChanged(sortedOrder)
        onSortDialogStatusChanged(sortDialog.none)
    }


    fun onCalendarPageLeft() {
        val currentStartDate = getStartOfWeek(_selectedDate.value)
        _selectedDate.value = currentStartDate.minusWeeks(1)
    }

    fun onCalendarPageRight() {
        val currentStartDate = getStartOfWeek(_selectedDate.value)
        _selectedDate.value = currentStartDate.plusWeeks(1)
    }
    fun onDeleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deletetask(task)
        }
    }
    fun changeExpandedId(id: Long?) {
    _expandedid.value = id
    }

  
    private fun getStartOfWeek(date: LocalDate): LocalDate {
        return date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    }




    private fun toDate(date: LocalDate, isSelected: Boolean) = CalendarUi.Date(
        date = date,
        isSelected = isSelected,
        isToday = date.isEqual(LocalDate.now())
    )

}




enum class SortedOrder {
    SORT_BY_RECENT,
    SORT_BY_DURATION,
    SORT_BY_NAME,
}
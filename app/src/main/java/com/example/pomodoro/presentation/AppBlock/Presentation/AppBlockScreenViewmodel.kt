package com.example.pomodoro.presentation.AppBlock.Presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pomodoro.presentation.AppBlock.data.local.Entity.InstalledPackage
import com.example.pomodoro.presentation.AppBlock.Domain.Repository.PackageInfoDataSourceRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class AppBlockScreenViewmodel @Inject constructor(
    private val PackageInfoDataSourceRepo: PackageInfoDataSourceRepo,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
     val searchQuery = savedStateHandle.getStateFlow(key = SEARCH_QUERY, initialValue = "")
    @OptIn(ExperimentalCoroutinesApi::class)
    val searchBarUiState: StateFlow<SearchBarUiState> = PackageInfoDataSourceRepo.getInstalledTask().flatMapLatest {
        data->

          searchQuery.flatMapLatest {
              query->
              if(searchQuery.value.trim().length <= MIN_QUERY_LENGTH){
                  flowOf(SearchBarUiState.emptySearchQuery(results = data))
              }
              else{
                  flowOf(SearchBarUiState.Success(results = data.filter { it.appName.contains(query,ignoreCase = true) }))
              }
          }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SearchBarUiState.loading
    )

    fun onQueryChanged(query:String){
        savedStateHandle[SEARCH_QUERY] = query
    }
    fun onClear(){
        savedStateHandle[SEARCH_QUERY]=""
    }

}

    sealed interface SearchBarUiState {
    data object loading : SearchBarUiState

    data class emptySearchQuery(val results: List<InstalledPackage>) : SearchBarUiState
    data class Success(val results: List<InstalledPackage>) : SearchBarUiState{
        fun isempty():Boolean = results.size == 0
    }
}
private const val SEARCH_QUERY = "searchQuery"
private const val MIN_QUERY_LENGTH = 3



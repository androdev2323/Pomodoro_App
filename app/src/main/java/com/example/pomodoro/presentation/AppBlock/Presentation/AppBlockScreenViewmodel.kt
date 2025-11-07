package com.example.pomodoro.presentation.AppBlock.Presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pomodoro.presentation.AppBlock.Domain.Repository.InstalledPackageRepo
import com.example.pomodoro.presentation.AppBlock.data.local.Entity.InstalledPackage
import com.example.pomodoro.presentation.AppBlock.Domain.Repository.PackageInfoDataSourceRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class AppBlockScreenViewmodel @Inject constructor(
    private val PackageInfoDataSourceRepo: PackageInfoDataSourceRepo,
    private val InstalledPackageRepo: InstalledPackageRepo,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
     val searchQuery = savedStateHandle.getStateFlow(key = SEARCH_QUERY, initialValue = "")
    private val localchanges= MutableStateFlow<Map<String, Boolean>>(emptyMap())
    @OptIn(ExperimentalCoroutinesApi::class)
    val searchBarUiState: StateFlow<SearchBarUiState> = PackageInfoDataSourceRepo.getInstalledTask().flatMapLatest {
        data->
        InstalledPackageRepo.getAllApps().flatMapLatest {
            result->
            data.map {
              list1->
             val app = result.find { list1.packageName == it.packageName }
             app?.let {
                 list1.copy(isenabled = app.isenabled)
             }

            }
            searchQuery.flatMapLatest { query ->
                if (searchQuery.value.trim().length <= MIN_QUERY_LENGTH) {
                    flowOf(SearchBarUiState.emptySearchQuery(results = data))
                } else {
                    flowOf(SearchBarUiState.Success(results = data.filter {
                        it.appName.contains(
                            query,
                            ignoreCase = true
                        )
                    }))
                }
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SearchBarUiState.loading
    )

    val mergedappsflow = combine(PackageInfoDataSourceRepo.getInstalledTask(),InstalledPackageRepo.getAllApps()){
            allApps,dbApps ->
       allApps.map {  app->
            val dbapp = dbApps.find { it.packageName == app.packageName }
            app.copy(isenabled = dbapp?.isenabled ?: false)
        }
    }
     val updatedAppsFlow= combine(mergedappsflow,localchanges){
            mergedlist,localchanges->
         mergedlist.map {
             app->
             val updatedstate = localchanges[app.packageName] ?: app.isenabled
             app.copy(isenabled = updatedstate)
         }
     }

    val searchBarUiState2: StateFlow<SearchBarUiState> = combine(searchQuery,updatedAppsFlow){
       query,mergedlist ->

        if (query.length <= MIN_QUERY_LENGTH) {
           (SearchBarUiState.emptySearchQuery(results = mergedlist))
        } else {
            (SearchBarUiState.Success(results = mergedlist.filter {
                it.appName.contains(
                    query,
                     ignoreCase = true
                )
            }))
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

    fun OnAppDeselected(installedPackage: InstalledPackage){
           localchanges.value= localchanges.value.toMutableMap().apply {
               this[installedPackage.packageName] = !installedPackage.isenabled          }
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



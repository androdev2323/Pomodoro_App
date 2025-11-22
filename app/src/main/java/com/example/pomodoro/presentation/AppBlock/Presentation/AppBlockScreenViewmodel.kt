package com.example.pomodoro.presentation.AppBlock.Presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pomodoro.presentation.AppBlock.Domain.Repository.InstalledPackageRepo
import com.example.pomodoro.presentation.AppBlock.data.local.Entity.InstalledPackage
import com.example.pomodoro.presentation.AppBlock.Domain.Repository.PackageInfoDataSourceRepo
import com.example.pomodoro.presentation.AppBlock.data.local.Entity.AndroidPackage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AppBlockScreenViewmodel @Inject constructor(
    private val PackageInfoDataSourceRepo: PackageInfoDataSourceRepo,
    private val InstalledPackageRepo: InstalledPackageRepo,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
     val searchQuery = savedStateHandle.getStateFlow(key = SEARCH_QUERY, initialValue = "")
    private val localchanges= MutableStateFlow<Map<String, Boolean>>(emptyMap())





   private val mergedappsflow = combine(PackageInfoDataSourceRepo.getInstalledTask(),InstalledPackageRepo.getAllApps()){
            allApps,dbApps ->
       allApps.map {  app->
            val dbapp = dbApps.find { it.packageName == app.packageName }
            app.copy(isenabled = dbapp?.isenabled ?: false)
        }

    }

   private   val updatedAppsFlow: Flow<List<InstalledPackage>> = combine(mergedappsflow,localchanges){
            mergedlist,localchanges->

         mergedlist.map {
             app->
             val updatedstate = localchanges[app.packageName] ?: app.isenabled
             app.copy(isenabled = updatedstate)
         }

     }
    val isChanged:StateFlow<Boolean> = combine(mergedappsflow,updatedAppsFlow){
        db,local ->
        val dbCount = db.filter { it.isenabled == true }.map { it.packageName }.toSet()
        val localCount = local.filter { it.isenabled == true }.map { it.packageName }.toSet()
        dbCount != localCount
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    val searchBarUiState: StateFlow<SearchBarUiState> = combine(searchQuery,updatedAppsFlow){
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

    fun onSaveClicked() {
        viewModelScope.launch {
        val appsToSave = updatedAppsFlow.first().map {
           AndroidPackage(
                packageName = it.packageName,
                appName = it.appName,
               isenabled = it.isenabled
               )
        }

            InstalledPackageRepo.insertAll(appsToSave)
            localchanges.value = emptyMap()
        }
    }

}

    sealed interface SearchBarUiState {
    data object loading : SearchBarUiState

    data class emptySearchQuery(val results: List<InstalledPackage>) : SearchBarUiState
    data class Success(val results: List<InstalledPackage>) : SearchBarUiState{
        fun isemmpty():Boolean = results.size == 0
    }
}
private const val SEARCH_QUERY = "searchQuery"
private const val MIN_QUERY_LENGTH = 3



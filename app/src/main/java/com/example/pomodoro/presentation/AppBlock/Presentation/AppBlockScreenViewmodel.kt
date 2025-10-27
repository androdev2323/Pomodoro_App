package com.example.pomodoro.presentation.AppBlock.Presentation

import android.content.pm.PackageInfo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pomodoro.presentation.AppBlock.Domain.Model.InstalledPackage
import com.example.pomodoro.presentation.AppBlock.Domain.Repository.installedPackageRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class AppBlockScreenViewmodel @Inject constructor(
    private val installedPackageRepo: installedPackageRepo
) : ViewModel(){

val appBlockUiState:StateFlow<AppBlockUiState> = installedPackageRepo.getInstalledTask().map {
    AppBlockUiState.Uistate(data = it)
}.stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5000),
    initialValue = AppBlockUiState.Loading
)

}

sealed interface AppBlockUiState{
    data object Loading:AppBlockUiState

    data class Uistate(
        val data:List<InstalledPackage>
    ):AppBlockUiState
}
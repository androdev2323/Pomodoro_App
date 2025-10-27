package com.example.pomodoro.presentation.AppBlock.Presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun AppBlockScreenRoute(viewmodel: AppBlockScreenViewmodel = hiltViewModel()){
val appBlockUiState by  viewmodel.appBlockUiState.collectAsStateWithLifecycle()
    AppBlockScreen(appBlockUiState = appBlockUiState)
}

@Composable
internal fun AppBlockScreen(appBlockUiState: AppBlockUiState) {
    when(appBlockUiState) {
        AppBlockUiState.Loading -> {}
        is AppBlockUiState.Uistate -> {
            LazyColumn (verticalArrangement = Arrangement.spacedBy(5.dp)){
                items(appBlockUiState.data){
                    println("$it.appName}  ${it.appIcon}")
                    Text(it.appName)
                }

            }
        }
    }
}


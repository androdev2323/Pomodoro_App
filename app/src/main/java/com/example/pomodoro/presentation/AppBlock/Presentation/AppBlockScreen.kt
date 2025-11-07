package com.example.pomodoro.presentation.AppBlock.Presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pomodoro.presentation.AppBlock.Components.AppBlockSearchBar
import com.example.pomodoro.presentation.AppBlock.Components.ToggleAppComponent
import com.example.pomodoro.presentation.AppBlock.data.local.Entity.InstalledPackage

@Composable
fun AppBlockScreenRoute(viewmodel: AppBlockScreenViewmodel = hiltViewModel()) {
    val state by viewmodel.searchBarUiState2.collectAsStateWithLifecycle()
    val searchQuery by viewmodel.searchQuery.collectAsStateWithLifecycle()
    AppBlockScreen(
        searchBarUistate = state,
        searchQuery = searchQuery,
        onQueryChanged = viewmodel::onQueryChanged,
        onClear = viewmodel::onClear,
        onSwitchClick = viewmodel::OnAppDeselected

    )
}

@Composable
internal fun AppBlockScreen(
    searchBarUistate: SearchBarUiState,
    searchQuery: String,
    onQueryChanged: (String) -> Unit,
    onClear: () -> Unit,
    onSwitchClick:(InstalledPackage) -> Unit
) {
    Column(){
        Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))
        AppBlockSearchBar(searchQuery = searchQuery, onQueryChanged = { onQueryChanged(it) }, onClearClicked = onClear)


        when (searchBarUistate) {


            is SearchBarUiState.Success -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    items(searchBarUistate.results, key = { app -> app.packageName }) { app ->
                        ToggleAppComponent(
                            packageinfo = app.packageName,
                            appicon = app.appIcon,
                            appname = app.appName,
                            checked = app.isenabled,
                            onCheckChanged = { onSwitchClick(app)}
                        )
                    }

                }
            }

            is SearchBarUiState.emptySearchQuery -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    items(searchBarUistate.results, key = { app -> app.packageName }) { app ->
                        ToggleAppComponent(
                            packageinfo = app.packageName,
                            appicon = app.appIcon,
                            appname = app.appName,
                            checked = app.isenabled,
                            onCheckChanged = { onSwitchClick(app)}
                        )
                    }

                }
            }

            SearchBarUiState.loading -> {}
        }
    }
}


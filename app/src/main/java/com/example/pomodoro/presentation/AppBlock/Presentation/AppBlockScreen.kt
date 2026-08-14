package com.example.pomodoro.presentation.AppBlock.Presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pomodoro.presentation.AppBlock.Components.AppBlockSearchBar
import com.example.pomodoro.presentation.AppBlock.Components.EmptySearchContent
import com.example.pomodoro.presentation.AppBlock.Components.FloatingBottomBanner
import com.example.pomodoro.presentation.AppBlock.Components.ToggleAppComponent
import com.example.pomodoro.presentation.AppBlock.data.local.Entity.InstalledPackage
import com.example.pomodoro.presentation.StopWatch.Domain.Model.TimerState
import com.example.pomodoro.presentation.components.PomodoroTopappbar

@Composable
fun AppBlockScreenRoute(viewmodel: AppBlockScreenViewmodel = hiltViewModel()) {
    val state by viewmodel.searchBarUiState.collectAsStateWithLifecycle()
    val timerState by viewmodel.timerStateEvent.collectAsStateWithLifecycle(null);
    val changes by viewmodel.isChanged.collectAsStateWithLifecycle()
    val searchQuery by viewmodel.searchQuery.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            PomodoroTopappbar(title = "")
        },

        floatingActionButton = {
            AnimatedVisibility(
                visible = changes,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
            ) {

                FloatingActionButton(onClick = viewmodel::onSaveClicked) {
                    Icon(imageVector = Icons.Filled.Save, contentDescription = "Save")
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        AppBlockScreen(
            modifier = Modifier.padding(
                start = it.calculateStartPadding(LocalLayoutDirection.current),
                top = it.calculateTopPadding(),
                end = it.calculateEndPadding(LocalLayoutDirection.current),
                bottom = 0.dp
            ),
            searchBarUistate = state,
            timerState = timerState,
            searchQuery = searchQuery,
            onQueryChanged = viewmodel::onQueryChanged,
            onClear = viewmodel::onClear,
            onSwitchClick = viewmodel::OnAppDeselected

        )
    }
}

@Composable
internal fun AppBlockScreen(
    modifier: Modifier = Modifier,
    searchBarUistate: SearchBarUiState,
    timerState: TimerState?,
    searchQuery: String,
    onQueryChanged: (String) -> Unit,
    onClear: () -> Unit,
    onSwitchClick: (InstalledPackage) -> Unit
) {
    val bannerVisible = (timerState !is TimerState.Finished)
    Box {
        Column(modifier = modifier.fillMaxHeight()) {

            AppBlockSearchBar(
                searchQuery = searchQuery,
                onQueryChanged = { onQueryChanged(it) },
                onClearClicked = onClear
            )
            when (searchBarUistate) {


                is SearchBarUiState.Success -> {
                    if (searchBarUistate.isemmpty()) {
                        EmptySearchContent()

                    } else {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            items(
                                searchBarUistate.results,
                                key = { app -> app.packageName }) { app ->
                                ToggleAppComponent(
                                    packageinfo = app.packageName,
                                    appicon = app.appIcon,
                                    appname = app.appName,
                                    checked = app.isenabled,
                                    onCheckChanged = { onSwitchClick(app) }
                                )
                            }
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
                                onCheckChanged = { onSwitchClick(app) }
                            )
                        }

                    }
                }

                SearchBarUiState.loading -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        items(10) {
                            ToggleAppComponent(
                                isLoading = true,
                                packageinfo = "",
                                appicon = null,
                                appname = "",
                                checked = false,
                                onCheckChanged = {}
                            )
                        }
                    }
                }
            }
        }
        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .zIndex(5f)
                .navigationBarsPadding(),
            visible = bannerVisible,
            enter = slideInVertically { it } + fadeIn(),
            exit = slideOutVertically { it } + fadeOut()
        ) {
            (timerState as? TimerState.Running)?.let {
                FloatingBottomBanner(
                    text = it.timeToString()
                )
            }
        }
        if (bannerVisible) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Color.White.copy(
                            alpha = if (bannerVisible) 0.6f else 1f
                        )
                    )
            )
        }

    }
}


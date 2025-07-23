package com.example.pomodoro.presentation.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.graphics.Insets
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pomodoro.presentation.BottomSheet.TaskBottomEvents
import com.example.pomodoro.presentation.BottomSheet.TaskBottomSheetContent
import com.example.pomodoro.presentation.BottomSheet.TaskBottomSheetViewModel
import com.example.pomodoro.presentation.BottomSheet.Task_BottomSheet
import com.example.pomodoro.presentation.HomeScreen.Entity.CalendarUi
import com.example.pomodoro.presentation.taskScreen.Components.dateRow
import java.time.LocalDate


@Composable
fun Task_Screen(modifier: Modifier = Modifier, viewmodel: HomeScreenViewmodel = hiltViewModel()) {
    val state by viewmodel.HomescreenState.collectAsState()
    TaskScreen(
        state,
        onArrowLeftClicked = {
            viewmodel.action(
                HomeScreenEvents.getDates(
                    startdate = state.dates!!.startdate.date.minusDays(1),
                    lastselectedDate = state.dates!!.selecteddate.date
                )
            )
        },
        onArrowRightClicked = {
            viewmodel.action(
                HomeScreenEvents.getDates(
                    startdate = state.dates!!.enddate.date.plusDays(2),
                    lastselectedDate = state.dates!!.selecteddate.date
                )
            )
        },
        onDateClicked = { viewmodel.onDateClicked(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    state: HomeScreenState,
    taskbottomviewmodel: TaskBottomSheetViewModel = hiltViewModel(),
    onAddTaskClicked: () -> Unit = { taskbottomviewmodel.action(TaskBottomEvents.OnShowBottomSheet) },
    onArrowLeftClicked: () -> Unit,
    onArrowRightClicked: () -> Unit,
    onDateClicked: (LocalDate) -> Unit
) {
    Scaffold(
        topBar = {
            Column(modifier = Modifier.statusBarsPadding()) {
                dateRow(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    Month = state.dates!!.selecteddate.date.month.name,
                    dates = state.dates!!.visbledates,
                    onArrowLeftClicked = onArrowLeftClicked,
                    onArrowRightClicked = onArrowRightClicked,
                    onDateClicked = onDateClicked
                )
            }


        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTaskClicked, containerColor = MaterialTheme.colorScheme.primaryContainer ){
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {

        Column(modifier = Modifier.padding(it)) {


            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(items = state.taskList, key = { it.taskid }) {
                    Text(text = it.name)
                }
            }
        }
        Task_BottomSheet(viewmodel = taskbottomviewmodel)
        Spacer(modifier = Modifier.height(50.dp))
    }

}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBar(
    text: String,
    OnAddTaskClicked: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(text = text) },
        actions = {
            IconButton(onClick = OnAddTaskClicked) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            scrolledContainerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}

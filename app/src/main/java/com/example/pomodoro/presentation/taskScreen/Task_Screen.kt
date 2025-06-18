package com.example.pomodoro.presentation.HomeScreen

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pomodoro.presentation.BottomSheet.TaskBottomEvents
import com.example.pomodoro.presentation.BottomSheet.TaskBottomSheetContent
import com.example.pomodoro.presentation.BottomSheet.TaskBottomSheetViewModel
import com.example.pomodoro.presentation.BottomSheet.Task_BottomSheet
import com.example.pomodoro.presentation.taskScreen.Components.dateRow


@Composable
fun Task_Screen(viewmodel: HomeScreenViewmodel = hiltViewModel() ) {
    val state by viewmodel.HomescreenState.collectAsState()
    TaskScreen(state)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    state: HomeScreenState,
    taskbottomviewmodel: TaskBottomSheetViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopBar(
                text = "Home",
                OnAddTaskClicked = { taskbottomviewmodel.action(TaskBottomEvents.OnShowBottomSheet)},
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
            )
        }
    ) {

        Column(modifier = Modifier.padding(it)) {
            when (state) {
                is HomeScreenState.Success -> {
                    dateRow(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        Month = "April", dates = state.dates.visbledates,
                        onArrowLeftClicked = {},
                    ) { }
                }

                is HomeScreenState.Error -> {}
                is HomeScreenState.Loadng -> {}

                else -> {}
            }
        Task_BottomSheet(viewmodel = taskbottomviewmodel)

        }
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
                Icon(imageVector = Icons.Filled.Add, contentDescription = "")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.background
        ),
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}

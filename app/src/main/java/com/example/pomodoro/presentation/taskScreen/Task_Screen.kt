package com.example.pomodoro.presentation.HomeScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.graphics.Insets
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pomodoro.Navigation.Stopwatch
import com.example.pomodoro.R
import com.example.pomodoro.presentation.BottomSheet.TaskBottomEvents
import com.example.pomodoro.presentation.BottomSheet.TaskBottomSheetContent
import com.example.pomodoro.presentation.BottomSheet.TaskBottomSheetViewModel
import com.example.pomodoro.presentation.BottomSheet.Task_BottomSheet
import com.example.pomodoro.presentation.HomeScreen.Entity.CalendarUi
import com.example.pomodoro.presentation.taskScreen.Components.DailyTaskCard
import com.example.pomodoro.presentation.taskScreen.Components.SortedSheet
import com.example.pomodoro.presentation.taskScreen.Components.TaskDetailElement
import com.example.pomodoro.presentation.taskScreen.Components.TaskItemCard
import com.example.pomodoro.presentation.taskScreen.Components.dateRow
import java.time.LocalDate


@Composable
fun Task_Screen(
    modifier: Modifier = Modifier,
    viewmodel: HomeScreenViewmodel = hiltViewModel(),
    navController: NavController,
) {
    val state by viewmodel.homescreenState.collectAsStateWithLifecycle()
    if(state.dates != null) {
        TaskScreen(
            state,
            onArrowLeftClicked = {
                viewmodel.onCalendarPageLeft()

            },
            onArrowRightClicked = {
                viewmodel.onCalendarPageRight()
            },
            completedtask = state.completedTaskCount,
            totaltask = state.totalTaskCount,
            onDateClicked = { viewmodel.onDateClicked(it) },
            OnTaskClicked = {
                if (!(state.dates?.selecteddate?.date!!.isBefore(LocalDate.now()))) navController.navigate(
                    route = Stopwatch(it)
                )
            },
            onSortClicked = {
                viewmodel.onSortDialogStatusChanged(it)
            },
            onSortOrderDismissed = {
                viewmodel.onSortDialogDismissed(it)
            }
        )
    }


}


@Composable
internal fun TaskScreen(
    state: HomeScreenState,
    totaltask:Int,
    completedtask:Int,
    taskbottomviewmodel: TaskBottomSheetViewModel = hiltViewModel(),
    onAddTaskClicked: () -> Unit = { taskbottomviewmodel.action(TaskBottomEvents.OnShowBottomSheet) },
    onArrowLeftClicked: () -> Unit,
    onArrowRightClicked: () -> Unit,
    onDateClicked: (LocalDate) -> Unit,
    onSortOrderDismissed:(sortedOrder:SortedOrder) -> Unit,
    onSortClicked:(sortDialog:sortDialog) -> Unit,
    OnTaskClicked: (Int) -> Unit
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
            FloatingActionButton(
                onClick = onAddTaskClicked,
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
 Log.d("order",state.sortedOrder.name)
        Column(modifier = Modifier
            .padding(it)
            .fillMaxWidth()) {
            if(state.sortStatus  == sortDialog.tasksort){
               SortedSheet(sortedOrder =state.sortedOrder ) {
                   onSortOrderDismissed(it)
               }
            }
            Spacer(Modifier.height(10.dp))
            DailyTaskCard(totalTask =totaltask , completedTask = completedtask)
            Spacer(Modifier.height(10.dp))
            Row(modifier = Modifier.padding(10.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    "Today's Tasks",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    )
                IconButton(onClick = {
                     onSortClicked(sortDialog.tasksort)
                }) {
                    Icon(painter = painterResource(R.drawable.sort_24px), contentDescription = "", tint = MaterialTheme.colorScheme.primary)
                }
            }
            Spacer(Modifier.height(5.dp))
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = state.taskList, key = { it.taskid }) { task ->
                    TaskItemCard(
                        progress = "${
                            ((task.completedshifts * 100) / task.totatshifts)
                        }",
                        onClick = { OnTaskClicked(task.taskid.toInt()) },
                        taskTitle = task.name,
                        taskDesc = task.totatshifts
                    )
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

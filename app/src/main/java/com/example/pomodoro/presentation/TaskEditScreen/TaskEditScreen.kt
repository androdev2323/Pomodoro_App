package com.example.pomodoro.presentation.TaskEditScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pomodoro.Navigation.HomeScreenRoutes
import com.example.pomodoro.presentation.BottomSheet.Components.CustomNumberPicker
import com.example.pomodoro.presentation.BottomSheet.Components.DatePickerEditText
import com.example.pomodoro.presentation.BottomSheet.Components.TaskEdittext
import com.example.pomodoro.presentation.BottomSheet.TaskBottomEvents
import com.example.pomodoro.presentation.TaskEditScreen.Components.AnimatedButton
import com.example.pomodoro.presentation.TaskEditScreen.Components.DurationPicker
import com.example.pomodoro.presentation.components.PomodoroTopappbar
import com.example.pomodoro.ui.theme.PomodoroTheme
import kotlin.concurrent.timerTask



@Composable
fun TaskEditScreen(
    viewmodel: TaskEditScreenViewmodel = hiltViewModel(),
    onBackClicked: () -> Unit
) {
    val state = viewmodel.TaskEditScreenFormState.collectAsStateWithLifecycle()
    val uistate = viewmodel.uiState.collectAsStateWithLifecycle()

        TaskEditScreen(
            modifier = Modifier,
            state = state.value,
            uiState = uistate.value,
            onTaskNameChange = viewmodel::onTasknameChange,
            onDateChange = viewmodel::onDateChange,
            onDurationChange = viewmodel::onDurationChange,
            onSaveClicked = viewmodel::onSaveTask,
            onBackClicked = onBackClicked,
            afterAnimation = viewmodel::onResetUistate

        )

}


@Composable
fun TaskEditScreen(
    modifier: Modifier = Modifier,
    state: TaskEditScreenFormState,
    uiState: Uistate,
    onTaskNameChange: (String) -> Unit,
    onDateChange: (Long) -> Unit,
    onDurationChange: (Int) -> Unit,
    onSaveClicked: () -> Unit,
    onBackClicked: () -> Unit,
    afterAnimation: () -> Unit
) {
           var currentDuratione by remember{
               mutableStateOf(-1)
           }
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Edit Text",
            modifier = modifier
                .align(Alignment.Start)
                .padding(horizontal = 10.dp, vertical = 2.dp),
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
        )
        Text(
            text = "Add or modify your task details",
            modifier = modifier
                .align(Alignment.Start)
                .padding(start = 10.dp, bottom = 20.dp)

        )
        Card(modifier = Modifier,colors=CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(30.dp)
            ) {
                TaskEdittext(
                    title = "Title",
                    hint = "Task title",
                    value = state.taskname.data,
                    errormessage = state.taskname.errorMessage,
                    onvalueChange = {
                        onTaskNameChange(it)
                    })
                Spacer(Modifier.height(10.dp))


                DatePickerEditText { onDateChange(it) }

                Spacer(Modifier.height(10.dp))


                Spacer(Modifier.height(10.dp))

                Text(
                    "Duration",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 40.dp),
                    textAlign = TextAlign.Left
                )
                DurationPicker(
                    duration = listOf(1,2,3,4,5,6,7,8,9,10,11,12),
                    dialog = {},
                    selectedInt = currentDuratione ,
                    onClick = {
                        currentDuratione = it
                    }
                )




                AnimatedButton(uiState, onClick = { onSaveClicked() }, afterAnimation = {
                    afterAnimation()
                    onBackClicked()
                })

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskEditPreview() {
    // 1. Create dummy state for the form
    val dummyState = TaskEditScreenFormState(
        taskname = TaskEditElementState<String>(data = "message", errorMessage = null),
        duration = 5
    )


    val dummyUiState = Uistate.Idle
    PomodoroTheme {


            TaskEditScreen(
                state = dummyState,
                uiState = dummyUiState,
                onTaskNameChange = {},
                onDateChange = {},
                onDurationChange = {},
                onSaveClicked = {},
                onBackClicked = {},
                afterAnimation = {}
            )
        }

}

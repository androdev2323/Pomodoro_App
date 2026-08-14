package com.example.pomodoro.presentation.TaskEditScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pomodoro.presentation.BottomSheet.Components.DatePickerEditText
import com.example.pomodoro.presentation.BottomSheet.Components.TaskEdittext
import com.example.pomodoro.presentation.TaskEditScreen.Components.AnimatedButton
import com.example.pomodoro.presentation.TaskEditScreen.Components.DurationPicker
import com.example.pomodoro.presentation.TaskEditScreen.Components.NumberPickerBottomSheet
import com.example.pomodoro.ui.theme.PomodoroTheme


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
    var currentDuration = state.duration
    var showBottomSheet by remember { mutableStateOf(false) }
    NumberPickerBottomSheet(showBottomSheet) {
        showBottomSheet = false
        currentDuration = it
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
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 10.dp, vertical = 2.dp),
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.SemiBold, fontSize = 28.sp),
        )
        Text(
            text = "Add or modify your task details",
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(start = 10.dp, bottom = 20.dp)

        )
              Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),){
                  Column(
                      verticalArrangement = Arrangement.Top,
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
                          duration = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
                          dialog = { showBottomSheet = true },
                          selectedInt = currentDuration,
                          onClick = {
                              onDurationChange(it)
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

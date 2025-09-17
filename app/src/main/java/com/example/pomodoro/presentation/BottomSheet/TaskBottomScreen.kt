package com.example.pomodoro.presentation.BottomSheet

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pomodoro.presentation.BottomSheet.Components.DatePickerEditText
import com.example.pomodoro.presentation.BottomSheet.Components.TaskEdittext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Task_BottomSheet(viewmodel: TaskBottomSheetViewModel) {

    val state by viewmodel.TaskBottomSheetState.collectAsState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    if (state.isSheetVisible) {
        ModalBottomSheet(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            onDismissRequest = { viewmodel.action(TaskBottomEvents.OnHideBottomSheet) },
            sheetState = sheetState
        ) {
            TaskBottomSheetContent(viewmodel)
        }
    }
}

@Composable
fun TaskBottomSheetContent(viewmodel: TaskBottomSheetViewModel) {
    val state = viewmodel.TaskBottomSheetState.collectAsState()
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TaskEdittext(title = "Title", hint = "Task title", value = state.value.taskname) {
            viewmodel.action(TaskBottomEvents.OnTaskNameChange(it))
        }
        Spacer(Modifier.height(10.dp))


        DatePickerEditText { viewmodel.action(TaskBottomEvents.OnDateChange(it)) }

        Spacer(Modifier.height(10.dp))

        TaskEdittext(
            title = "Duration",
            hint = "Task Duration",
            value = state.value.duration.toString()
        ) {
            viewmodel.action(TaskBottomEvents.OnDurationChange(it.toInt()))
        }
        Spacer(Modifier.height(10.dp))
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            onClick = {
                viewmodel.action(TaskBottomEvents.OnSaveTask)
                viewmodel.action(TaskBottomEvents.OnHideBottomSheet)
            },
            modifier = Modifier.padding(10.dp),
            shape = RoundedCornerShape(size = 10.dp)
        ) {
            Text("Save")
        }


    }


}




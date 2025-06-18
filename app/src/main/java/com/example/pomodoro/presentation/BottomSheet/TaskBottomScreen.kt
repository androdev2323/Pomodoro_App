package com.example.pomodoro.presentation.BottomSheet

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pomodoro.presentation.BottomSheet.Components.DatePickerEditText
import com.example.pomodoro.presentation.BottomSheet.Components.TaskEdittext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Task_BottomSheet(  viewmodel: TaskBottomSheetViewModel ) {

    val state by viewmodel.TaskBottomSheetState.collectAsState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    if (state.isSheetVisible) {
        ModalBottomSheet(onDismissRequest = { viewmodel.action(TaskBottomEvents.OnHideBottomSheet) },
            sheetState=sheetState) {
                          TaskBottomSheetContent(viewmodel)
        }
    }
}

@Composable
fun TaskBottomSheetContent(viewmodel: TaskBottomSheetViewModel) {
    Column() {
        TaskEdittext(title = "Title", hint = "Task title") {
            viewmodel.action(TaskBottomEvents.OnTaskNameChange(it))
        }
        Spacer(Modifier.height(10.dp))


        DatePickerEditText { viewmodel.action(TaskBottomEvents.OnDateChange(it)) }

        Spacer(Modifier.height(10.dp))

        TaskEdittext(title = "Duration", hint = "Task Duration") {
            viewmodel.action(TaskBottomEvents.OnDurationChange(it.toInt()))
        }


    }




}

@Preview
@Composable
fun PreviewTaskBottomSheet() {
    TaskBottomSheetContent(TaskBottomSheetViewModel())

}



package com.example.pomodoro.presentation.BottomSheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pomodoro.presentation.BottomSheet.Components.DatePickerEditText
import com.example.pomodoro.presentation.BottomSheet.Components.TaskEdittext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Task_BottomSheet(viewmodel:TaskBottomSheetViewModel){
ModalBottomSheet(onDismissRequest ={} ) {

}
}

@Composable
fun TaskBottomSheet(viewmodel:TaskBottomSheetViewModel){
    Column(){
     TaskEdittext(title ="Title", hint = "Task title") {
      viewmodel.action(TaskBottomEvents.OnTaskNameChange(it))
     }
        Spacer(Modifier.height(10.dp))

        DatePickerEditText { viewmodel.action(TaskBottomEvents.OnDateChange(it)) }

    }

}

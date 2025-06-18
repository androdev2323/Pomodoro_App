package com.example.pomodoro.presentation.BottomSheet.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun DatePickerEditText(onDateSelected: (Long) -> Unit) {
    var isdatepickervsible by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }
    Column() {
        Text(text = "Date", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
        OutlinedTextField(value = selectedDate, onValueChange = {}, trailingIcon = {
            IconButton(onClick = { isdatepickervsible = true }) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "Select Date")
            }
        })
        if (isdatepickervsible) {
            datePicker(onDateSelected = {
                selectedDate = it?.let { it1 -> formatDate(it1) }!!
               it?.let { onDateSelected(it) }
            }, onDismiss = { isdatepickervsible = false })
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun datePicker(onDateSelected: (Long?) -> Unit, onDismiss: () -> Unit) {
    val datePickerState = rememberDatePickerState()
    DatePickerDialog(
        onDismissRequest = onDismiss, confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("Ok")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismiss()
            }) {
                Text("Cancel")
            }
        }) {
        DatePicker(state = datePickerState)
    }

}

fun formatDate(timestamp: Long?): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(timestamp)

}
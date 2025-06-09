package com.example.pomodoro.presentation.taskScreen.Components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

import com.example.pomodoro.presentation.HomeScreen.Components.DateSelectItem
import com.example.pomodoro.presentation.HomeScreen.Entity.CalendarUi

@Composable
fun dateRow(Month:String,dates:List<CalendarUi.Date>,onArrowLeftClicked:() -> Unit,onArrowRightClicked:() -> Unit ) {
    Row {
        Text(
            text = Month,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
        )
        IconButton(onClick = { onArrowLeftClicked() }) {
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "")

            LazyRow() {
                items(dates) {
                    DateSelectItem(it)
                }
            }
            IconButton(onClick = { onArrowRightClicked() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = ""
                )
            }
        }
    }
}
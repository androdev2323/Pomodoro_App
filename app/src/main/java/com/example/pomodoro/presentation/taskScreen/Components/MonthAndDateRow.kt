package com.example.pomodoro.presentation.taskScreen.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import com.example.pomodoro.presentation.HomeScreen.Components.DateSelectItem
import com.example.pomodoro.presentation.HomeScreen.Entity.CalendarUi

@Composable
fun dateRow(modifier: Modifier,Month:String,dates:List<CalendarUi.Date>,onArrowLeftClicked:() -> Unit,onArrowRightClicked:() -> Unit ) {

        Row(modifier = Modifier.padding(5.dp),verticalAlignment = Alignment.CenterVertically){
            Text(
                text = Month,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),

            )
            IconButton(onClick = { onArrowLeftClicked() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = ""
                )
            }

                LazyRow(modifier = Modifier.weight(1f)) {
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

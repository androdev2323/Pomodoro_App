package com.example.pomodoro.presentation.HomeScreen.Components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pomodoro.presentation.HomeScreen.Entity.CalendarUi
import java.time.LocalDate

@Composable
fun DateSelectItem(date: CalendarUi.Date,onClick:(LocalDate) -> Unit) {
    Card(
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp)
            .clickable { onClick(date.date) },
        border = BorderStroke(2.dp, color = if(date.isSelected && date.isToday) MaterialTheme.colorScheme.tertiary else if(date.isSelected) MaterialTheme.colorScheme.secondary else Color.Transparent),
        colors=CardDefaults.cardColors(
            containerColor = if(date.isToday) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainer
        ),

    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .height(50.dp)
                .width(50.dp)
                , verticalArrangement = Arrangement.spacedBy(3.2.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = date.day,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = date.date.dayOfMonth.toString(),

                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
            )


            Text(
                text = date.date.month.toString().take(3),
                style = MaterialTheme.typography.bodySmall,
            )


        }
    }
}


@Preview
@Composable
private fun Prevew_dateselect() {

}
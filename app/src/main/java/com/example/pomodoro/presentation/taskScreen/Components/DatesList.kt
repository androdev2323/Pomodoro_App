package com.example.pomodoro.presentation.HomeScreen.Components

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pomodoro.presentation.HomeScreen.Entity.CalendarUi
import java.time.LocalDate

@Composable
fun DateSelectItem(date: CalendarUi.Date,onClick:(LocalDate) -> Unit) {
    Card(
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp)
            .clickable { onClick(date.date) },
        colors=CardDefaults.cardColors(
            containerColor = if(date.isSelected || date.isToday) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
        )
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .height(40.dp)
                .width(40.dp)
                , verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = date.day,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = date.date.dayOfMonth.toString(),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodySmall,
            )

        }
    }
}


@Preview
@Composable
private fun Prevew_dateselect() {

}
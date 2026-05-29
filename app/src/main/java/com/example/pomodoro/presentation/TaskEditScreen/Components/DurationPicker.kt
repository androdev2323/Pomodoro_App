package com.example.pomodoro.presentation.TaskEditScreen.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pomodoro.ui.theme.PomodoroTheme


@Composable
fun DurationPicker(duration: List<Int>, selectedInt: Int, dialog: () -> Unit,onClick:(Int) -> Unit) {

    LazyRow(
        modifier = Modifier
            .height(45.dp)
            .clip(shape = RoundedCornerShape(15.dp))
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(15.dp)
            )
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {

        itemsIndexed(duration) { index, item ->

            Row(
                modifier = Modifier
                        .then(if (selectedInt == item) Modifier
                            .clip(shape = RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.primary) else Modifier)

                    .clickable { onClick(item) },
                verticalAlignment = Alignment.CenterVertically,

                ) {

                Text(
                    modifier = Modifier
                        .padding(
                            vertical = 6.dp,
                            horizontal = 6.dp
                        )
                       ,
                    color = if (selectedInt != item) Color.Black else Color.White,
                    text = (item.toString() + " h")
                )
                Box(
                    modifier = Modifier
                        .fillParentMaxHeight()
                        .padding(top = 3.dp, bottom = 3.dp, start = 4.dp)
                        .width(1.dp)
                        .background(Color.LightGray)
                )
            }
        }
        item {


            Text(
                modifier = Modifier
                    .padding(
                        horizontal = 8.dp
                    )
                    .clickable { dialog() },
                text = "Custom"
            )
        }

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DurationPickerPreview() {
    PomodoroTheme {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            DurationPicker(listOf(1, 2, 3, 4, 5), selectedInt = 2, onClick = { }, dialog = {})
        }
    }
}
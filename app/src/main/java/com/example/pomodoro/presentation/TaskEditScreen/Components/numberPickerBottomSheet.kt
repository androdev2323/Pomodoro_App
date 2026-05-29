package com.example.pomodoro.presentation.TaskEditScreen.Components

import android.util.Log
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pomodoro.presentation.BottomSheet.Components.SectionBar
import com.example.pomodoro.ui.theme.PomodoroTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberPickerBottomSheet() {
    ModalBottomSheet(
        onDismissRequest = {},
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        ) {

        NumberPickerBottomSheetContent()

    }
}

@Composable
private fun NumberPickerBottomSheetContent(
    numberList: List<Int> = listOf(
        1,
        2,
        3,
        4,
        5,
        6,
        7,
        8,
        9,
        10,
        11,
        12
    )
) {
    val lazyListState = rememberLazyListState()
    val currentlySelectedTime by remember {  derivedStateOf {
        val visibleWindow = lazyListState.layoutInfo.viewportSize
        val centre = visibleWindow.height/2
        lazyListState.layoutInfo.visibleItemsInfo.minByOrNull {
        val itemCentre = it.offset + it.size/2
            kotlin.math.abs(centre - itemCentre)
        }?.index ?: 0
    }


}
    Log.d("ganesh",currentlySelectedTime.toString())
    val flingstate = rememberSnapFlingBehavior(lazyListState)
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            modifier = Modifier.padding(top = 24.dp, start = 24.dp, end = 24.dp),
            text = "Set Duration",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            ),
        )
        Text(
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 24.dp, top = 12.dp),
            text = "select the number of hours for your focus session",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
            textAlign = TextAlign.Center
        )
        val lazyColumnHeight = 250.dp
        val itemHeight = 56.dp
        val edgePadding = (lazyColumnHeight - itemHeight) / 2
        Box {
            SectionBar(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .align(Alignment.Center)
                    .height(58.dp)
            )
            LazyColumn(
                modifier = Modifier.heightIn(max = 250.dp),
                state = lazyListState,
                flingBehavior = flingstate,
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(top = edgePadding, bottom = edgePadding)

            ) {
                itemsIndexed(numberList, key = { index, item -> item }) { index, item ->

                      if (index != 0)
                          HorizontalDivider(
                              thickness = 1.dp,
                              modifier = Modifier.fillMaxWidth(0.6f)
                          )

                      Row(
                          modifier = Modifier
                              .fillMaxWidth()
                              .height(56.dp)
                              .padding(),
                          horizontalArrangement = Arrangement.spacedBy(
                              8.dp,
                              alignment = Alignment.CenterHorizontally
                          ),
                          verticalAlignment = Alignment.CenterVertically
                      ) {
                          Text(
                              modifier = Modifier,
                              text = item.toString(),
                              style = MaterialTheme.typography.bodyMedium.copy(fontSize = 20.sp),
                          )
                          Text(
                              modifier = Modifier,
                              text = "Hrs",
                              style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp)
                          )
                      }
                      if (index == numberList.size - 1)
                          HorizontalDivider(
                              thickness = 1.dp,
                              modifier = Modifier.fillMaxWidth(0.6f))








                }
            }

        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {},
            modifier = Modifier
                .padding(top = 24.dp, start = 24.dp, end = 24.dp, bottom = 12.dp)
                .fillMaxWidth()
                .height(46.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Submit")
        }

        OutlinedButton(
            onClick = {},
            modifier = Modifier
                .padding( start = 24.dp, end = 24.dp, bottom = 12.dp)
                .fillMaxWidth()
                .height(46.dp),
            colors =ButtonDefaults.outlinedButtonColors(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Cancel")
        }
    }
    }



@Preview
@Composable
private fun PreviewNumberPickerBottomSheet() {
    PomodoroTheme {
        NumberPickerBottomSheet()
    }
}

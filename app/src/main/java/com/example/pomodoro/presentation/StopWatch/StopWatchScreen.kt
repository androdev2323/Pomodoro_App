package com.example.pomodoro.presentation.StopWatch


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pomodoro.Util.TimeFormatter
import com.example.pomodoro.presentation.StopWatch.Components.StopwatchTimerComponent


@Composable
fun StopwatchScreen(
    modifier: Modifier = Modifier,
    viewmodel: StopWatchViewmodel = hiltViewModel()
) {
    val state by viewmodel.state.collectAsStateWithLifecycle()

    when (state.taskitem) {
        null -> {}
        else -> StopwatchScreenContent(
            modifier = modifier,
            state = state,
            viewmodel::onPaused,
            viewmodel::onResumed,
            viewmodel::onFinished
        )

    }
}

@Composable
fun StopwatchScreenContent(
    modifier: Modifier = Modifier,
    state: StopwatchScreenState,
    onPause: () -> Unit,
    onResumed: (Long) -> Unit,
    onFinished: () -> Unit
) {

    val duration = state.taskitem!!.remaining_time


    val time = when (val timer = state.timerState) {
        is StopWatchState.Running -> timer.time
        is StopWatchState.Pause -> timer.time
        else -> 0L
    }

    val progress: Long = when (val timer = state.timerState) {
        is StopWatchState.Running -> (((duration - timer.time) * 100) / duration)
        is StopWatchState.Pause -> (((duration - timer.time) * 100) / duration)
        else -> 0L
    }



    Column(
        modifier.padding(20.dp).fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StopwatchTimerComponent(
            modifier = Modifier
                .size(400.dp)
                .align(Alignment.CenterHorizontally),
            time = TimeFormatter.longtoTime(time),
            progress = progress
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            when (state.timerState) {
                is StopWatchState.Running -> {

                    Spacer(modifier.height(1.dp))
                    IconButton(onClick = { onPause() }) {
                        Icon(Icons.Filled.Add, "", tint = MaterialTheme.colorScheme.primary)
                    }
                }

                is StopWatchState.Pause -> {

                    IconButton(onClick = { onResumed(state.timerState.time) }) {
                        Icon(Icons.Filled.PlayArrow, "", tint = MaterialTheme.colorScheme.primary)
                    }
                }

                is StopWatchState.Finished -> {
                    IconButton(onClick = {onFinished()}) {
                        Icon(Icons.AutoMirrored.Filled.Send, "", tint = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }

    }
}
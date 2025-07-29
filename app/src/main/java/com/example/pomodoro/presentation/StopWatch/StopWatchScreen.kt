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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pomodoro.Data.PomdoroContract
import com.example.pomodoro.R
import com.example.pomodoro.Util.TimeFormatter
import com.example.pomodoro.presentation.StopWatch.Components.StopwatchTimerComponent
import com.example.pomodoro.presentation.StopWatch.Components.TaskInfoCard


@Composable
fun StopwatchScreen(
    modifier: Modifier = Modifier,
    viewmodel: StopWatchViewmodel = hiltViewModel()
) {
    val state by viewmodel.state.collectAsStateWithLifecycle()
    val uistate by viewmodel.uistate.collectAsStateWithLifecycle()

    when (state.taskitem) {
        null -> {}
        else -> StopwatchScreenContent(
            modifier = modifier,
            state = state,
            uistate = uistate,
            viewmodel::onPaused,
            viewmodel::onResumed,
            viewmodel::onFinished
        )

    }
}

@Composable
internal fun StopwatchScreenContent(
    modifier: Modifier = Modifier,
    state: StopwatchScreenState,
    uistate:ViewmodelState,
    onPause: () -> Unit,
    onResumed: (Long) -> Unit,
    onFinished: () -> Unit
) {

    val duration = state.taskitem!!.remaining_time
    val animationid = if (state.taskitem.session_type == PomdoroContract.POMODORO_SHORTBREAK
        || state.taskitem.session_type == PomdoroContract.POMODORO_lONGBREAK
    ) {
        R.raw.breaktime
    } else {
        R.raw.breaktime2
    }


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
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 30.dp, horizontal = 5.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TaskInfoCard(
            tastkTitle = state.taskitem.name,
            taskDuration = (state.taskitem.duration.toString()),
            totalSessions = state.taskitem.totatshifts.toString(),
            completedSessions = state.taskitem.completedshifts.toString()
        )
        Column(
            modifier
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StopwatchTimerComponent(
                modifier = Modifier
                    .size(350.dp),
                time = TimeFormatter.longtoTime(time),
                animationid = animationid,
                progress = progress
            )
            Spacer(Modifier.height(30.dp))
            Text(
                text = if (state.taskitem.session_type == PomdoroContract.POMODORO_WORK) {
                    "Stay focus for ${TimeFormatter.longtoTime(state.taskitem.remaining_time.toLong())}"
                } else {
                     "Have chill untill ${TimeFormatter.longtoTime(state.taskitem.remaining_time.toLong())}"
                },
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(30.dp))


            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                when (state.timerState) {
                    is StopWatchState.Running -> {

                        Spacer(modifier.height(1.dp))
                        IconButton(
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(70.dp)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            onClick = { onPause() }) {
                            Icon(

                                painter = painterResource(R.drawable.ic_pause),
                                "",
                                modifier = Modifier.size(25.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    is StopWatchState.Pause -> {

                        IconButton(
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(70.dp)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            onClick = { onResumed(state.timerState.time) }) {
                            Icon(

                                painter = painterResource(R.drawable.baseline_play_arrow_24),
                                "",
                                modifier = Modifier.size(25.dp),
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }

                    is StopWatchState.Finished -> {
                        Log.d("test" , uistate.isEnabled.toString())
                        IconButton(
                            enabled = uistate.isEnabled,
                            modifier = Modifier
                                .clip(CircleShape)
                                .alpha(if (uistate.isEnabled) 1f else 0.5f)
                                .size(70.dp)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            onClick = { onFinished() }) {
                            Icon(
                                Icons.AutoMirrored.Filled.Send,
                                "",
                                modifier = Modifier.size(25.dp),
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }

        }

    }
}




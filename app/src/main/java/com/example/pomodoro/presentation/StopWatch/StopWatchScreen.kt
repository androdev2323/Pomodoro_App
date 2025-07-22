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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.pomodoro.Data.PomdoroContract
import com.example.pomodoro.R
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
        modifier
            .padding(20.dp)
            .fillMaxSize(),
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


        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            when (state.timerState) {
                is StopWatchState.Running -> {

                    Spacer(modifier.height(1.dp))
                    IconButton(modifier = Modifier.clip(CircleShape).size(70.dp).background(MaterialTheme.colorScheme.primaryContainer),onClick = { onPause() }) {
                        Icon(

                            painter = painterResource(R.drawable.ic_pause),
                            "",
                            modifier = Modifier.size(25.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                is StopWatchState.Pause -> {

                    IconButton(  modifier = Modifier.clip(CircleShape).size(70.dp).background(MaterialTheme.colorScheme.primaryContainer),onClick = { onResumed(state.timerState.time) }) {
                        Icon(

                            painter = painterResource(R.drawable.baseline_play_arrow_24),
                            "",
                            modifier = Modifier.size(25.dp),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

                is StopWatchState.Finished -> {
                    IconButton(modifier = Modifier.clip(CircleShape).size(70.dp).background(MaterialTheme.colorScheme.primaryContainer),onClick = { onFinished() }) {
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




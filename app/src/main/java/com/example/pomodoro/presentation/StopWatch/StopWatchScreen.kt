package com.example.pomodoro.presentation.StopWatch

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun StopwatchScreen(
    modifier: Modifier = Modifier,
    viewmodel: StopWatchViewmodel = hiltViewModel()
    ){
        val state by viewmodel.state.collectAsStateWithLifecycle()

       StopwatchScreenContent(modifier = modifier, state = state,viewmodel::onPaused)

}

@Composable
fun StopwatchScreenContent(modifier: Modifier ,state :StopWatchState,onPause:() -> Unit,) {
    Column(modifier.padding(20.dp)) {

        when(state){
        is StopWatchState.Running -> {
            Text(state.time.toString())
            Spacer(modifier.height(10.dp))
            IconButton(onClick = { onPause()}) {
                Icon( Icons.Filled.PlayArrow,"")
            }
        }
            is StopWatchState.Pause -> {
                Text(state.time.toString())

            }
        }

    }
}
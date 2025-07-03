package com.example.pomodoro.presentation.StopWatch

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun StopwatchScreen(
    modifier: Modifier = Modifier,
    viewmodel: StopWatchViewmodel = hiltViewModel()
    ){


       StopwatchScreenContent()

}

@Composable
fun StopwatchScreenContent(state :StopWatchState){

}
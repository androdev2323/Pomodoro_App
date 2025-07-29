package com.example.pomodoro.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pomodoro.presentation.HomeScreen.Task_Screen
import com.example.pomodoro.presentation.StopWatch.StopwatchScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
){
    NavHost(navController, startDestination =Home) {
       composable<Home>{
          Task_Screen(navController = navController)
       }
        composable<Stopwatch>{

            StopwatchScreen()
        }
    }
}
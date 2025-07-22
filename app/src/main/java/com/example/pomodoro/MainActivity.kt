package com.example.pomodoro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pomodoro.presentation.HomeScreen.TaskScreen
import com.example.pomodoro.presentation.HomeScreen.Task_Screen
import com.example.pomodoro.presentation.StopWatch.StopwatchScreen
import com.example.pomodoro.ui.theme.PomodoroTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
          PomodoroTheme {
              Scaffold {
                  StopwatchScreen(modifier = Modifier.padding(it))


              }
          }



            /*   Task_Screen()    */
}
}
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
Text(
text = "Hello $name!",
modifier = modifier
)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
PomodoroTheme {
Greeting("Android")
}
}
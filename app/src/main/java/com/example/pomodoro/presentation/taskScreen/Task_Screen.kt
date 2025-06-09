package com.example.pomodoro.presentation.HomeScreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun Task_Screen() {

}

@Composable
fun TaskScreen(
viewmodel: HomeScreenViewmodel
) {
    Scaffold(

    ){
        it
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBar(text: String,OnAddTaskClicked:() -> Unit,scrollBehavior: TopAppBarScrollBehavior,modifier: Modifier =Modifier) {
    TopAppBar(title = { Text(text = text) },
        actions = {
              IconButton(onClick = OnAddTaskClicked) {
                  Icon(imageVector = Icons.Filled.Add, contentDescription = "")
              }
        },
        colors=TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.background
        ),
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}

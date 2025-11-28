package com.example.pomodoro.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.pomodoro.ui.theme.PomodoroTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PomodoroTopappbar(title:String,) {
   TopAppBar(title = { Text( text = title, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) }, navigationIcon ={ Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Arrow back") }, colors = TopAppBarDefaults.topAppBarColors( containerColor = MaterialTheme.colorScheme.primaryContainer))

}

@Preview("TopAppbar-Light")
@Composable
private fun TopAppBarPreviewLight() {
   PomodoroTheme {
      PomodoroTopappbar(title = "AppBlock Screen")

   }
}

@Preview("TopAppbar-Dark", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES )
@Composable
private fun AppBarPreviewDark() {
   PomodoroTheme {
      PomodoroTopappbar(title = "AppBlock Screen")

   }
}
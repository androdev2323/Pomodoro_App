package com.example.pomodoro.presentation.AppBlock.Components

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.pomodoro.R
import com.example.pomodoro.ui.theme.PomodoroTheme

@Composable
fun ToggleAppComponent(packageinfo: String, appname: String, appicon: ImageBitmap) {
    Row(modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing).fillMaxWidth().height(100.dp).background(MaterialTheme.colorScheme.primary), verticalAlignment = Alignment.CenterVertically){
        AppIcon(appicon = appicon)

    }
}

@Composable
fun AppIcon(appicon: ImageBitmap) {
    Image(
        bitmap = appicon,
        contentDescription = "",
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .size(90.dp)
            .padding( 15.dp)
            .clip(
                RoundedCornerShape(10.dp)
            )
            .background(color = Color.White)

    )
}

@Preview( showSystemUi = true)
@Composable
private fun ToggleAppComponentPreview() {
    Column {
        PomodoroTheme {
            val context = LocalContext.current
            ResourcesCompat.getDrawable(context.resources, R.drawable.ic_pause, null)!!.toBitmap()
                .asImageBitmap()
                ?.let { ToggleAppComponent("packageinfo", "appname", it) }

        }
    }
}
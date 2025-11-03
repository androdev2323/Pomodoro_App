package com.example.pomodoro.presentation.AppBlock.Components

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.pomodoro.R
import com.example.pomodoro.ui.theme.PomodoroTheme

@Composable
fun ToggleAppComponent(packageinfo: String, appname: String, appicon: ImageBitmap) {
    Row(
        modifier = Modifier

            .fillMaxWidth()
            .height(100.dp)
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        AppIcon(appicon = appicon)
        Column(verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.padding(horizontal = 20.dp).weight(1f)) {
            Text(
                appname,
                maxLines = 1,
                overflow = TextOverflow.MiddleEllipsis,

                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

            Text(packageinfo,  style = MaterialTheme.typography.bodyMedium)

        }
        Switch(

            checked = true,
            onCheckedChange = {}
        )


    }
}

@Composable
fun AppIcon(appicon: ImageBitmap) {
    ElevatedCard(elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
            modifier = Modifier
            .size(width = 50.dp, height = 50.dp)

    ) {
        Image(
            bitmap = appicon,
            modifier = Modifier.size(50.dp),
            contentDescription = "",
            contentScale = ContentScale.Fit,


            )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ToggleAppComponentPreview() {
    Column() {
        PomodoroTheme {
            val context = LocalContext.current
            ResourcesCompat.getDrawable(context.resources, R.drawable.ic_pause, null)!!.toBitmap()
                .asImageBitmap()
                ?.let { ToggleAppComponent("packageinfo", "appname", it) }

        }
    }
}
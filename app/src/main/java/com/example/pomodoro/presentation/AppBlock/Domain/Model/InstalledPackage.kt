package com.example.pomodoro.presentation.AppBlock.Domain.Model

import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.ImageBitmap

data class InstalledPackage(
    val appName:String,
    val appIcon:ImageBitmap,
    val packageName:String
)

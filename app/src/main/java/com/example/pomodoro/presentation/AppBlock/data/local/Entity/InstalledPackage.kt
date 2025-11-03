package com.example.pomodoro.presentation.AppBlock.data.local.Entity

import androidx.compose.ui.graphics.ImageBitmap
import androidx.room.Entity

@Entity(tableName = "Apps")
data class InstalledPackage(
    val appName:String,
    val appIcon:ImageBitmap,
    val packageName:String,
    val isenabled:Boolean = false
)

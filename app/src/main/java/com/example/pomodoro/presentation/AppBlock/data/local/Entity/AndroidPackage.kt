package com.example.pomodoro.presentation.AppBlock.data.local.Entity

import androidx.compose.ui.graphics.ImageBitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Apps")
data class AndroidPackage(
    val appName:String,

    @PrimaryKey val packageName:String,
    val isenabled:Boolean = false
)
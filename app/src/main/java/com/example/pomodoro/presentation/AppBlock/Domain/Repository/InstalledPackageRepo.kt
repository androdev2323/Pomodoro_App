package com.example.pomodoro.presentation.AppBlock.Domain.Repository

import android.content.pm.PackageInfo
import com.example.pomodoro.presentation.AppBlock.data.local.Entity.AndroidPackage
import com.example.pomodoro.presentation.AppBlock.data.local.Entity.InstalledPackage
import kotlinx.coroutines.flow.Flow

interface InstalledPackageRepo {

    suspend fun insertAll(installedPackage: List<AndroidPackage>)
    fun getAllApps():Flow<List<AndroidPackage>>
}
package com.example.pomodoro.presentation.AppBlock.Domain.Repository

import android.content.pm.PackageInfo
import com.example.pomodoro.presentation.AppBlock.data.local.Entity.InstalledPackage
import kotlinx.coroutines.flow.Flow

interface InstalledPackageRepo {

    suspend fun insertAll(installedPackage: List<InstalledPackage>)
    fun getAllApps():Flow<List<InstalledPackage>>
}
package com.example.pomodoro.presentation.AppBlock.Domain.Repository

import android.content.pm.PackageInfo
import com.example.pomodoro.presentation.AppBlock.Domain.Model.InstalledPackage
import kotlinx.coroutines.flow.Flow

interface installedPackageRepo{

    fun getInstalledTask():Flow<List<InstalledPackage>>
}
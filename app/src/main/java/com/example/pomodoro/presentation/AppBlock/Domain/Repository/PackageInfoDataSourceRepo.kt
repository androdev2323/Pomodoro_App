package com.example.pomodoro.presentation.AppBlock.Domain.Repository

import com.example.pomodoro.presentation.AppBlock.data.local.Entity.InstalledPackage
import kotlinx.coroutines.flow.Flow

interface PackageInfoDataSourceRepo{

    fun getInstalledTask():Flow<List<InstalledPackage>>
}
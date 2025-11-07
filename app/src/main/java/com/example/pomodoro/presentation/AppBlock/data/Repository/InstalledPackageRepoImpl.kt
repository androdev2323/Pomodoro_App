package com.example.pomodoro.presentation.AppBlock.data.Repository

import android.content.pm.PackageInfo
import com.example.pomodoro.presentation.AppBlock.Domain.Repository.InstalledPackageRepo
import com.example.pomodoro.presentation.AppBlock.data.local.Entity.AndroidPackage
import com.example.pomodoro.presentation.AppBlock.data.local.Entity.InstalledPackage
import com.example.pomodoro.presentation.AppBlock.data.local.dao.InstalledPackageDao
import kotlinx.coroutines.flow.Flow

class InstalledPackageRepoImpl(private val dao: InstalledPackageDao):InstalledPackageRepo {
    override suspend fun insertAll(installedPackage: List<AndroidPackage>) {
        return dao.insertAll(installedPackage)
    }

    override fun getAllApps(): Flow<List<AndroidPackage>> {
        return dao.getAllInstalledPackages()
    }

}
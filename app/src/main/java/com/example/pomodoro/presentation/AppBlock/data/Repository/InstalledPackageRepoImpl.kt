package com.example.pomodoro.presentation.AppBlock.data.Repository

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import com.example.pomodoro.presentation.AppBlock.Domain.Model.InstalledPackage
import com.example.pomodoro.presentation.AppBlock.Domain.Repository.installedPackageRepo
import com.example.pomodoro.presentation.AppBlock.data.InstalledAppsDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class InstalledPackageRepoImpl(val datasource:InstalledAppsDataSource):installedPackageRepo {
    override fun getInstalledTask(): Flow<List<InstalledPackage>> = flow {

           val list= datasource.getListOfPackages()
            emit(list)
        }.flowOn(Dispatchers.IO)

    }

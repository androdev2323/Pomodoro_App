package com.example.pomodoro.presentation.AppBlock.data.Repository

import com.example.pomodoro.presentation.AppBlock.data.local.Entity.InstalledPackage
import com.example.pomodoro.presentation.AppBlock.Domain.Repository.PackageInfoDataSourceRepo
import com.example.pomodoro.presentation.AppBlock.data.InstalledAppsDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PackageInfoDataSourceRepoImpl(val datasource:InstalledAppsDataSource):PackageInfoDataSourceRepo {
    override fun getInstalledTask(): Flow<List<InstalledPackage>> = flow {

           val list= datasource.getListOfPackages()
            emit(list)
        }.flowOn(Dispatchers.IO)

    }

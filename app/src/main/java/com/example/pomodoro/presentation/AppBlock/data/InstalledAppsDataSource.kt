package com.example.pomodoro.presentation.AppBlock.data

import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmap
import com.example.pomodoro.presentation.AppBlock.data.local.Entity.InstalledPackage

import dagger.hilt.android.qualifiers.ApplicationContext


class InstalledAppsDataSource( @ApplicationContext private val context:Context){
private val packagemanager = context.packageManager


    fun getListOfPackages():List<InstalledPackage>{
      val list = packagemanager.getInstalledPackages(PackageManager.GET_META_DATA).map {
            packageInfo ->
            InstalledPackage(
                packageName =  packageInfo.packageName,
                appIcon = packageInfo.applicationInfo!!.loadIcon(packagemanager).toBitmap().asImageBitmap(),
                appName = packageInfo.applicationInfo!!.loadLabel(packagemanager).toString() ,

                )
        }
        return list
    }
}
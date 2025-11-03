package com.example.pomodoro.presentation.AppBlock.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.pomodoro.presentation.AppBlock.data.local.Entity.InstalledPackage
import kotlinx.coroutines.flow.Flow

@Dao
  interface InstalledPackageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(installedPackage: List<InstalledPackage>)

    fun getAllInstalledPackages():Flow<List<InstalledPackage>>


}

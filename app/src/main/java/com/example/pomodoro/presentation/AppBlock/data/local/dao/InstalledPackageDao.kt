package com.example.pomodoro.presentation.AppBlock.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pomodoro.presentation.AppBlock.data.local.Entity.AndroidPackage
import com.example.pomodoro.presentation.AppBlock.data.local.Entity.InstalledPackage
import kotlinx.coroutines.flow.Flow

@Dao
  interface InstalledPackageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(installedPackage: List<AndroidPackage>)

    @Query("SELECT * FROM Apps")
    fun getAllInstalledPackages():Flow<List<AndroidPackage>>


}

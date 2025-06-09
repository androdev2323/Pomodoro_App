package com.example.pomodoro.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.pomodoro.local.ShiftType
import com.example.pomodoro.local.TypeConvertors.ShiftTypeConvertor

@Entity(
    foreignKeys = [ForeignKey(
        entity = Task::class,
        parentColumns = ["taskid"],
        childColumns = ["task_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
@TypeConverters(ShiftTypeConvertor::class)
data class Session(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo("taskid")
    val task_id: Long,
    val elapsedtime: Long,
    val totaltime: Long,
    val Shift_type: ShiftType,
)

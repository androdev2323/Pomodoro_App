package com.example.pomodoro.domain.repository

import com.example.pomodoro.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface taskrepo {

    fun gettaskbydate(date:Long): Flow<List<Task>>
    fun insertask(task:Task)
}
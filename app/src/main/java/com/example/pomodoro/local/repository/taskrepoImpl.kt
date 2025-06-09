package com.example.pomodoro.local.repository

import com.example.pomodoro.domain.model.Task
import com.example.pomodoro.domain.repository.taskrepo
import kotlinx.coroutines.flow.Flow

class taskrepoImpl:taskrepo {
    override fun gettaskbydate(date: Long): Flow<List<Task>> {
        TODO("Not yet implemented")
    }

    override fun insertask(task: Task) {
        TODO("Not yet implemented")
    }
}
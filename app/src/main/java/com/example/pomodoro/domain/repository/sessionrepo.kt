package com.example.pomodoro.domain.repository

import com.example.pomodoro.domain.model.Session

interface sessionrepo {
fun createsession(session: Session)
fun getcurrsession(taskid:Int):Session
}
package com.example.pomodoro.Util

import java.time.LocalDate
import java.time.ZoneOffset

fun LocalDate.toUtcStartOfDayMillis(): Long {
    return this.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
}
package com.example.pomodoro.Data

object PomdoroContract {

    const val  POMODORO_lONGBREAK = "lONG_BREAK"
    const val POMODORO_SHORTBREAK= "SHORT_BREAK"
    const val POMODORO_WORK="WORK"
    const val POMODORO_IDLE="IDLE"


    const val STATUS_PENDING="PENDING"
    const val STATUS_PAUSED="PAUSED"
    const val STATUS_RUNNING="RUNNING"
    const val STATUS_COMPLETED="COMPLETED"

    const val DEFAULT_WORKDURATION = 25 * 60000
    const val DEFAULT_SHORTBREAKDURATION= 1 * 60000
    const val DEFAULT_LONGBREAKDURATION= 30* 60000
    const val LONGBREAK_SESSIONS=4

}
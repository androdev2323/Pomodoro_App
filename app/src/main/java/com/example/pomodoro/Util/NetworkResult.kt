package com.example.pomodoro.Util

sealed class NetworkResult<T> {
     data class Success<T>(val data:T):NetworkResult<T>()
    class Error<T>(val message:String?):NetworkResult<T>()
}
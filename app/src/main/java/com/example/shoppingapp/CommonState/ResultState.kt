package com.example.shoppingapp.CommonState
sealed class ResultState<T> {
    data class Success<T>(val data: T) : ResultState<T>()
    data class Error<T>(val error: String) : ResultState<T>()
    data class Loading<T>(val data: T? = null) : ResultState<T>()
}
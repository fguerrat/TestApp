package com.lacalera.testapp.data.common

sealed class Resource<out T> {
    class Success<T>(val data: T) : Resource<T>()
    class Error(val message: String, val cause: Throwable? = null) : Resource<Nothing>()
    object Unauthorized : Resource<Nothing>()
    object NetworkError : Resource<Nothing>()
}

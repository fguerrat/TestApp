package com.lacalera.testapp.data.remote.model

data class ResponseGenericDTO<T>(
    val error: String? = null,
    val data: T? = null,
    val isValid: Boolean = false
)


package com.lacalera.testapp.data.remote.utils

import com.google.gson.Gson
import com.lacalera.testapp.data.remote.model.ResponseGenericDTO
import retrofit2.Response
import java.io.IOException

object ApiResponseHandler {
    private val gson = Gson()
    fun <T> handleResponse(
        response: Response<ResponseGenericDTO<T>>,
        defaultError: String = "An unknown error occurred"
    ): ResponseGenericDTO<T> {
        return if (response.isSuccessful) {
            response.body() ?: ResponseGenericDTO(
                data = null,
                error = "Empty response body",
                isValid = false
            )
        } else {
            parseErrorResponse(response, defaultError)
        }
    }

    private fun <T> parseErrorResponse(
        response: Response<*>, 
        defaultError: String
    ): ResponseGenericDTO<T> {
        return try {
            val errorBody = response.errorBody()?.string()
            if (!errorBody.isNullOrEmpty()) {
                try {
                    // Try to parse the error response as a ResponseGenericDTO
                    val errorResponse = gson.fromJson(
                        errorBody,
                        ResponseGenericDTO::class.java
                    )
                    ResponseGenericDTO(
                        data = null,
                        error = errorResponse.error ?: defaultError,
                        isValid = false
                    )
                } catch (e: Exception) {
                    // If parsing fails, return the raw error body
                    ResponseGenericDTO(
                        data = null,
                        error = errorBody,
                        isValid = false
                    )
                }
            } else {
                ResponseGenericDTO(
                    data = null,
                    error = "${response.code()}: ${response.message()}",
                    isValid = false
                )
            }
        } catch (e: IOException) {
            ResponseGenericDTO(
                data = null,
                error = "Network error: ${e.message ?: defaultError}",
                isValid = false
            )
        } catch (e: Exception) {
            ResponseGenericDTO(
                data = null,
                error = e.message ?: defaultError,
                isValid = false
            )
        }
    }
}

suspend fun <T> Response<ResponseGenericDTO<T>>.handleApiResponse(
    defaultError: String = "An unknown error occurred"
): ResponseGenericDTO<T> = ApiResponseHandler.handleResponse(this, defaultError)

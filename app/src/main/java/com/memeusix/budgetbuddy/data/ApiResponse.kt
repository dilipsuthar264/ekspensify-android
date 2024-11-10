package com.memeusix.budgetbuddy.data


sealed class ApiResponse<out T>(
    val data: T?, val errorResponse: ErrorResponseModel?
) {
    data class Success<out T>(private val response: T?) :
        ApiResponse<T>(data = response, errorResponse = null)

    data class Failure<out T>(private val error: ErrorResponseModel?) :
        ApiResponse<T>(data = null, errorResponse = error)

    data object Loading : ApiResponse<Nothing>(data = null, errorResponse = null)
    data object Idle : ApiResponse<Nothing>(data = null, errorResponse = null)
}

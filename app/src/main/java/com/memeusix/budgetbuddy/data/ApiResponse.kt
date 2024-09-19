package com.memeusix.budgetbuddy.data

sealed class ApiResponse<T>(
    val isSuccess : Boolean,
    val data : T?,
    val errorResponse : String?
) {
    class Success<T>(val responseData : T?) : ApiResponse<T>(isSuccess = true, data = responseData, errorResponse = null)
    class Failure<T>(val errorData: String) : ApiResponse<T>(isSuccess = false, data = null, errorResponse = errorData)
}
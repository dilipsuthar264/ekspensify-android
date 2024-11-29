package com.memeusix.budgetbuddy.data

interface BaseViewModel {
    suspend fun <T> handleData(
        currentData: ApiResponse<T>, call: suspend () -> ApiResponse<T>
    ): ApiResponse<T> {
        return when (val response = call.invoke()) {
            is ApiResponse.Success -> {
                response
            }

            is ApiResponse.Failure -> {
                ApiResponse.Failure(
                    currentData = currentData.data, error = response.errorResponse
                )
            }

            else -> {
                currentData
            }
        }
    }
}
package com.memeusix.budgetbuddy.data

import android.util.Log
import android.util.MalformedJsonException
import org.json.JSONObject
import retrofit2.Response
import java.net.ConnectException

/**
 * this is base repository which is used for handle response
 */
interface BaseRepository {

    /**
     * T == BaseModel<>
     */
    suspend fun <T> handleResponse(call: suspend () -> Response<T>): ApiResponse<T> {
        try {
            val response = call.invoke()
            return when {
                response.isSuccessful -> {
                    ApiResponse.Success(response.body())
                }

                else -> {
                    handleFailureResponse(response)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "handleResponse: $e")
            return handleException(e)
        }
    }

    /**
     * Handle Failure response
     */
    fun <T> handleFailureResponse(response: Response<T>): ApiResponse<T> {
        response.errorBody()?.let {
            val jsonObject = JSONObject(it.string())
            val errorMessage = if (jsonObject.has("message")) {
                jsonObject.getString("message")
            } else {
                ""
            }
            return ApiResponse.Failure(errorMessage)
        }
        return ApiResponse.Failure("Something went wrong")
    }

    /**
     * Handle Exception
     */
    fun <T> handleException(e: Exception?): ApiResponse<T> {
        e?.let {
            return when (it) {
                is ConnectException -> {
                    ApiResponse.Failure("No internet connection")
                }

                is MalformedJsonException -> {
                    ApiResponse.Failure("Bad response")
                }

                else -> {
                    ApiResponse.Failure("Unknown error")
                }
            }
        }
        return ApiResponse.Failure("Something went wrong")
    }


    /**
     * T = data
     */
    fun <T> handleRequest(response: ApiResponse<BaseModel<T>>): BaseModel<T> {
        return response.data?.let {
            if (response.isSuccess) BaseModel(
                success = true,
                data = it.data,
                message = it.message,
                code = it.code
            ) else BaseModel.error(response.errorResponse)
        } ?: run {
            Log.e(TAG, "handleRequest: ${response.errorResponse}")
            return BaseModel.error(response.errorResponse)
        }
    }


    companion object {
        val TAG: String = BaseRepository::class.java.name
    }

}
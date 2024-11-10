package com.memeusix.budgetbuddy.data

import android.util.Log
import android.util.MalformedJsonException
import com.google.gson.Gson
import okio.EOFException
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
            Log.e(TAG, "handleResponse: $response", )
            return when {
                response.isSuccessful -> {
                    ApiResponse.Success(response = response.body())
                }

                else -> {
                    handleFailureResponse(response)
                }
            }
        }catch (e :EOFException){
            Log.e(TAG, "handleResponse: Empty response body (EOFException)")
            return ApiResponse.Success(response = null)
        }
        catch (e: Exception) {
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
            val error: ErrorResponseModel = if (jsonObject.has("error")) {
                val errorJson = jsonObject.getJSONObject("error").toString()
                Gson().fromJson(errorJson, ErrorResponseModel::class.java)
            } else {
                ErrorResponseModel()
            }
            return ApiResponse.Failure(error = error)
        }
        return ApiResponse.Failure(ErrorResponseModel(message = "Something went wrong"))
    }

    /**
     * Handle Exception
     */
    fun <T> handleException(e: Exception?): ApiResponse<T> {
        e?.let {
            return when (it) {
                is ConnectException -> {
                    ApiResponse.Failure(ErrorResponseModel(message = "No internet connection"))
                }

                is MalformedJsonException -> {
                    ApiResponse.Failure(ErrorResponseModel(message = "Bad response"))
                }

                else -> {
                    ApiResponse.Failure(ErrorResponseModel(message = "Unknown error"))
                }
            }
        }
        return ApiResponse.Failure(ErrorResponseModel(message = "Something went wrong"))
    }


//    /**
//     * T = data
//     */
//    fun <T> handleRequest(response: ApiResponse<T>): BaseModel<T> {
//        return response.data?.let {
//            if (response.isSuccess) BaseModel(
//                success = true,
//                data = it,
//                code = response.code
//            ) else BaseModel.error(response.errorResponse)
//        } ?: run {
//            Log.e(TAG, "handleRequest: ${response.errorResponse}")
//            return BaseModel.error(response.errorResponse)
//        }
//    }


    companion object {
        val TAG: String = BaseRepository::class.java.name
    }

}
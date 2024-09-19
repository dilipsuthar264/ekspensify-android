package com.memeusix.budgetbuddy.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BaseModel<T>(
    @SerializedName("success")
    @Expose
    val success: Boolean? = null,

    @SerializedName("code")
    @Expose
    val code: Int? = STATUS_IDLE,

    @SerializedName("message")
    @Expose
    val message: String? = null,

    @SerializedName("data")
    @Expose
    val data: T? = null,
) : Serializable {
    companion object {
        private const val STATUS_IDLE = 0
        private const val STATUS_LOADING = 1
        private const val STATUS_ERROR = 2

        fun <T> loading(): BaseModel<T> {
            return BaseModel(code = STATUS_LOADING, message = "Loading..")
        }

        fun <T> clear(): BaseModel<T> {
            return BaseModel(code = STATUS_IDLE)
        }

        fun <T> error(data: String?): BaseModel<T> {
            return BaseModel(
                success = false,
                data = null,
                message = data,
                code = STATUS_ERROR
            )
        }

    }

    fun isLoading(): Boolean {
        return code == STATUS_LOADING
    }

    fun isSuccess(): Boolean {
        return code in (200..300)
    }

    fun isError(): Boolean {
        return code == STATUS_ERROR
    }
}
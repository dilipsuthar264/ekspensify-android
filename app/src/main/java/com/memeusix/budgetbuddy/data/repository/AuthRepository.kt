package com.memeusix.budgetbuddy.data.repository

import com.memeusix.budgetbuddy.data.ApiResponse
import com.memeusix.budgetbuddy.data.BaseRepository
import com.memeusix.budgetbuddy.data.model.requestModel.AuthRequestModel
import com.memeusix.budgetbuddy.data.model.responseModel.AuthResponseModel
import com.memeusix.budgetbuddy.data.model.responseModel.UserResponseModel
import com.memeusix.budgetbuddy.data.services.AuthApi
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApi: AuthApi
) : BaseRepository {

    suspend fun signUpWithGoogle(
        idToken: String
    ): ApiResponse<AuthResponseModel> {
        return handleResponse { authApi.signUpWithGoogle(idToken) }
    }

    suspend fun signInWithGoogle(
        idToken: String
    ): ApiResponse<AuthResponseModel> {
        return handleResponse { authApi.signInWithGoogle(idToken) }
    }

    suspend fun register(
        authRequestModel: AuthRequestModel
    ): ApiResponse<UserResponseModel> {
        return handleResponse { authApi.register(authRequestModel) }
    }

    suspend fun login(
        authRequestModel: AuthRequestModel
    ): ApiResponse<AuthResponseModel> {
        return handleResponse { authApi.login(authRequestModel) }
    }

    suspend fun sendOtp(
        authRequestModel: AuthRequestModel
    ): ApiResponse<Any> {
        return handleResponse { authApi.sendOtp(authRequestModel) }
    }

}
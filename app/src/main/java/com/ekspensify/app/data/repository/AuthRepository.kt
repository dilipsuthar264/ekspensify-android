package com.ekspensify.app.data.repository

import com.ekspensify.app.data.ApiResponse
import com.ekspensify.app.data.BaseRepository
import com.ekspensify.app.data.model.requestModel.AuthRequestModel
import com.ekspensify.app.data.model.responseModel.AuthResponseModel
import com.ekspensify.app.data.model.responseModel.UserResponseModel
import com.ekspensify.app.data.services.AuthApi
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
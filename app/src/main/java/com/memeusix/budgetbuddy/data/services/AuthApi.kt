package com.memeusix.budgetbuddy.data.services

import com.memeusix.budgetbuddy.data.model.requestModel.AuthRequestModel
import com.memeusix.budgetbuddy.data.model.responseModel.AuthResponseModel
import com.memeusix.budgetbuddy.data.model.responseModel.UserResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {
    @FormUrlEncoded
    @POST("auth/google-signup")
    suspend fun signUpWithGoogle(
        @Field("id_token") idToken: String
    ): Response<AuthResponseModel>

    @FormUrlEncoded
    @POST("auth/google-signin")
    suspend fun signInWithGoogle(
        @Field("id_token") idToken: String
    ): Response<AuthResponseModel>

    @POST("auth/signup")
    suspend fun register(
        @Body authRequestModel: AuthRequestModel
    ): Response<UserResponseModel>

    @POST("auth/signin")
    suspend fun login(
        @Body authRequestModel: AuthRequestModel
    ): Response<AuthResponseModel>

    @POST("auth/send-otp")
    suspend fun sendOtp(
        @Body authRequestModel: AuthRequestModel
    ): Response<Any>

}
package com.memeusix.budgetbuddy.data.services

import com.memeusix.budgetbuddy.data.model.responseModel.UserResponseModel
import retrofit2.Response
import retrofit2.http.GET

interface ProfileApi {
    @GET("users/me")
    suspend fun getMe(): Response<UserResponseModel>
}
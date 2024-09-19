package com.memeusix.budgetbuddy.data.services
import com.memeusix.budgetbuddy.data.BaseModel
import com.memeusix.budgetbuddy.data.model.AuthRequestModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("login")
    suspend fun login(
        @Body authRequestModel: AuthRequestModel
    ) : Response<BaseModel<Any>>

    @POST("register")
    suspend fun register(
        @Body authRequestModel: AuthRequestModel
    ) : Response<BaseModel<Any>>
}
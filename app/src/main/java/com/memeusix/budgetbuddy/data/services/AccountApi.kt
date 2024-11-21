package com.memeusix.budgetbuddy.data.services

import com.memeusix.budgetbuddy.data.model.requestModel.AccountRequestModel
import com.memeusix.budgetbuddy.data.model.responseModel.AccountResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface AccountApi {
    @POST("accounts")
    suspend fun createAccount(
        @Body accountRequestModel: AccountRequestModel
    ): Response<AccountResponseModel>

    @PATCH("accounts/{id}")
    suspend fun updateAccount(
        @Path("id") accountId: Int,
        @Body accountRequestModel: AccountRequestModel
    ): Response<AccountResponseModel>

    @DELETE("accounts/{id}")
    suspend fun deleteAccount(
        @Path("id") accountId: Int
    ): Response<AccountResponseModel>

    @GET("accounts")
    suspend fun getAllAccounts(): Response<List<AccountResponseModel>>
}
package com.memeusix.budgetbuddy.data.services

import retrofit2.http.POST

interface TransactionApi {

    @POST("transactions")
    suspend fun createTransaction(

    )


}
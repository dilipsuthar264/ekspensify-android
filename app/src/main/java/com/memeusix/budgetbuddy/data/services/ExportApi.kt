package com.memeusix.budgetbuddy.data.services

import com.memeusix.budgetbuddy.data.model.requestModel.ExportRequestModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ExportApi {
    @POST("transactions/request-statement")
    suspend fun requestStatements(
        @Body exportRequestModel: ExportRequestModel
    ): Response<Any>
}
package com.memeusix.ekspensify.data.services

import com.memeusix.ekspensify.data.model.requestModel.ExportRequestModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ExportApi {
    @POST("transactions/request-statement")
    suspend fun requestStatements(
        @Body exportRequestModel: ExportRequestModel
    ): Response<Any>
}
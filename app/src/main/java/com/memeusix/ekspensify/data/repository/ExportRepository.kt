package com.memeusix.ekspensify.data.repository

import com.memeusix.ekspensify.data.ApiResponse
import com.memeusix.ekspensify.data.BaseRepository
import com.memeusix.ekspensify.data.model.requestModel.ExportRequestModel
import com.memeusix.ekspensify.data.services.ExportApi
import javax.inject.Inject

class ExportRepository @Inject constructor(
    private val exportApi: ExportApi
) : BaseRepository {
    suspend fun requestStatements(
        exportRequestModel: ExportRequestModel
    ): ApiResponse<Any> {
        return handleResponse { exportApi.requestStatements(exportRequestModel) }
    }
}
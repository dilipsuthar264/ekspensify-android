package com.ekspensify.app.data.repository

import com.ekspensify.app.data.ApiResponse
import com.ekspensify.app.data.BaseRepository
import com.ekspensify.app.data.model.requestModel.ExportRequestModel
import com.ekspensify.app.data.services.ExportApi
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
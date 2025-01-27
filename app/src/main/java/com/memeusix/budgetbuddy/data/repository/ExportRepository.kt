package com.memeusix.budgetbuddy.data.repository

import com.memeusix.budgetbuddy.data.ApiResponse
import com.memeusix.budgetbuddy.data.BaseRepository
import com.memeusix.budgetbuddy.data.model.requestModel.ExportRequestModel
import com.memeusix.budgetbuddy.data.services.ExportApi
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
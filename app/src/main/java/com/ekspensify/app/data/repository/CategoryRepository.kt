package com.ekspensify.app.data.repository

import com.ekspensify.app.data.ApiResponse
import com.ekspensify.app.data.BaseRepository
import com.ekspensify.app.data.model.requestModel.InsightsQueryModel
import com.ekspensify.app.data.model.responseModel.CategoryInsightsResponseModel
import com.ekspensify.app.data.model.responseModel.CategoryResponseModel
import com.ekspensify.app.data.model.responseModel.CustomIconModel
import com.ekspensify.app.data.services.CategoryApi
import com.ekspensify.app.ui.dashboard.transactions.data.getFormattedDateRange
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val categoryApi: CategoryApi
) : BaseRepository {

    suspend fun getCategories(): ApiResponse<List<CategoryResponseModel>> {
        return handleResponse { categoryApi.getCategories() }
    }

    suspend fun getCustomIcons(): ApiResponse<List<CustomIconModel>> {
        return handleResponse { categoryApi.getCustomIcons() }
    }

    suspend fun createCategory(
        categoryResponseModel: CategoryResponseModel
    ): ApiResponse<CategoryResponseModel> {
        return handleResponse { categoryApi.createCategory(categoryResponseModel) }
    }

    suspend fun deleteCategory(
        categoryId: Int
    ): ApiResponse<CategoryResponseModel> {
        return handleResponse { categoryApi.deleteCategory(categoryId) }
    }

    suspend fun getCategoryInsights(
        insightsQueryModel: InsightsQueryModel
    ): ApiResponse<List<CategoryInsightsResponseModel>> {
        return handleResponse {
            val (startDate, endDate) = insightsQueryModel.dateRange.getFormattedDateRange()
            categoryApi.getCategoryInsights(
                type = insightsQueryModel.type.name,
                startDate = startDate,
                endDate = endDate
            )
        }
    }
}
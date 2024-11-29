package com.memeusix.budgetbuddy.data.repository

import com.memeusix.budgetbuddy.data.ApiResponse
import com.memeusix.budgetbuddy.data.BaseRepository
import com.memeusix.budgetbuddy.data.model.responseModel.CategoryResponseModel
import com.memeusix.budgetbuddy.data.model.responseModel.CustomIconModel
import com.memeusix.budgetbuddy.data.services.CategoryApi
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
}
package com.memeusix.budgetbuddy.data.services

import com.memeusix.budgetbuddy.data.model.responseModel.CategoryResponseModel
import com.memeusix.budgetbuddy.data.model.responseModel.CustomIconModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CategoryApi {

    @GET("categories")
    suspend fun getCategories(): Response<List<CategoryResponseModel>>

    @GET("categories/custom-icons")
    suspend fun getCustomIcons(): Response<List<CustomIconModel>>

    @POST("categories")
    suspend fun createCategory(
        @Body categoryResponseModel: CategoryResponseModel
    ): Response<CategoryResponseModel>


    @DELETE("categories/{id}")
    suspend fun deleteCategory(
        @Path("id") categoryId: Int
    ): Response<CategoryResponseModel>
}
package com.memeusix.budgetbuddy.data.repository

import com.memeusix.budgetbuddy.data.ApiResponse
import com.memeusix.budgetbuddy.data.BaseRepository
import com.memeusix.budgetbuddy.data.model.responseModel.UserResponseModel
import com.memeusix.budgetbuddy.data.services.ProfileApi
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val profileApi: ProfileApi
) : BaseRepository {
    
    suspend fun getMe(): ApiResponse<UserResponseModel> {
        return handleResponse { profileApi.getMe() }
    }

}
package com.memeusix.ekspensify.data.repository

import com.memeusix.ekspensify.data.ApiResponse
import com.memeusix.ekspensify.data.BaseRepository
import com.memeusix.ekspensify.data.model.responseModel.UserResponseModel
import com.memeusix.ekspensify.data.services.ProfileApi
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val profileApi: ProfileApi
) : BaseRepository {

    suspend fun getMe(): ApiResponse<UserResponseModel> {
        return handleResponse { profileApi.getMe() }
    }

    suspend fun updateMe(
        userResponseModel: UserResponseModel
    ): ApiResponse<UserResponseModel> {
        return handleResponse { profileApi.updateMe(userResponseModel) }
    }
}
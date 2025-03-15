package com.ekspensify.app.data.repository

import com.ekspensify.app.data.ApiResponse
import com.ekspensify.app.data.BaseRepository
import com.ekspensify.app.data.model.responseModel.UserResponseModel
import com.ekspensify.app.data.services.ProfileApi
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
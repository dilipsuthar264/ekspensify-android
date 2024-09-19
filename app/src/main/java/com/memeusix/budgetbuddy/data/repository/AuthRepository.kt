package com.memeusix.budgetbuddy.data.repository

import android.util.Log
import com.memeusix.budgetbuddy.data.BaseModel
import com.memeusix.budgetbuddy.data.BaseRepository
import com.memeusix.budgetbuddy.data.model.AuthRequestModel
import com.memeusix.budgetbuddy.data.services.AuthApi
import javax.inject.Inject
import kotlin.math.log

class AuthRepository @Inject constructor(
    private val authApi: AuthApi
) : BaseRepository {

    suspend fun login(
        authRequestModel: AuthRequestModel
    ): BaseModel<Any> {
          return handleRequest(handleResponse{authApi.login(authRequestModel)})
    }

    suspend fun register(
        authRequestModel: AuthRequestModel
    ): BaseModel<Any> {
        return handleRequest(handleResponse{authApi.register(authRequestModel)})
    }
}
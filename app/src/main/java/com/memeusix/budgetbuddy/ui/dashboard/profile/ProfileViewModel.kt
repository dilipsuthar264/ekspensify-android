package com.memeusix.budgetbuddy.ui.dashboard.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memeusix.budgetbuddy.data.ApiResponse
import com.memeusix.budgetbuddy.data.BaseViewModel
import com.memeusix.budgetbuddy.data.model.responseModel.UserResponseModel
import com.memeusix.budgetbuddy.data.repository.ProfileRepository
import com.memeusix.budgetbuddy.utils.SpUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository, val spUtils: SpUtils
) : ViewModel(), BaseViewModel {

    /**
     * Get Profile Details
     */
    private val _getMe = MutableStateFlow<ApiResponse<UserResponseModel>>(ApiResponse.Idle)
    val getMe = _getMe.asStateFlow()

    init {
        getMe()
    }

    fun getMe() {
        viewModelScope.launch {
            _getMe.value = ApiResponse.Loading(
                currentData = _getMe.value.data,
            )
            val response = handleData(_getMe.value) { profileRepository.getMe() }
            _getMe.value = response
            if (response is ApiResponse.Success) {
                spUtils.user = response.data
            }
        }
    }

    /**
     * Update me
     */
    private val _updateMe = MutableStateFlow<ApiResponse<UserResponseModel>>(ApiResponse.Idle)
    val updateMe = _updateMe.asStateFlow()

    fun updateMe(user: UserResponseModel) {
        viewModelScope.launch {
            _updateMe.value = ApiResponse.Loading(
                currentData = _updateMe.value.data,
            )
            val response = profileRepository.updateMe(user)
            _updateMe.value = response
            if (response is ApiResponse.Success) {
                spUtils.user = response.data
            }
            _getMe.value = response
        }
    }

}
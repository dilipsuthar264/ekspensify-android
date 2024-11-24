package com.memeusix.budgetbuddy.ui.dashboard.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memeusix.budgetbuddy.data.ApiResponse
import com.memeusix.budgetbuddy.data.model.responseModel.UserResponseModel
import com.memeusix.budgetbuddy.data.repository.ProfileRepository
import com.memeusix.budgetbuddy.utils.SpUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository, private val spUtils: SpUtils
) : ViewModel() {

    /**
     * get User from spUtils
     */
    private val _user = MutableStateFlow(spUtils.user)
    val user = _user.asStateFlow()

    /**
     * Get Profile Details
     */
    private val _getMe = MutableStateFlow<ApiResponse<UserResponseModel>>(ApiResponse.Idle)
    val getMe: StateFlow<ApiResponse<UserResponseModel>> = _getMe.onStart {
        getMe()
    }.stateIn(
        scope = viewModelScope, initialValue = ApiResponse.Idle, started = SharingStarted.Lazily
    )

    fun getMe() {
        viewModelScope.launch {
            _getMe.value = ApiResponse.Loading(
                currentData = _getMe.value.data, currentError = null
            )
            val response = profileRepository.getMe()
            _getMe.value = response
        }
    }


    fun updateUser(user: UserResponseModel) {
        _user.value = user
        spUtils.user = user
    }


    /**
     * Update me
     */
    private val _updateMe = MutableStateFlow<ApiResponse<UserResponseModel>>(ApiResponse.Idle)
    val updateMe = _updateMe.asStateFlow()

    fun updateMe(user: UserResponseModel) {
        viewModelScope.launch {
            _updateMe.value = ApiResponse.Loading(
                currentData = _updateMe.value.data, currentError = null
            )
            val response = profileRepository.updateMe(user)
            _updateMe.value = response
        }
    }

    fun logout() {
        spUtils.logout()
    }

}
package com.memeusix.budgetbuddy.ui.acounts.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memeusix.budgetbuddy.data.ApiResponse
import com.memeusix.budgetbuddy.data.BaseViewModel
import com.memeusix.budgetbuddy.data.model.requestModel.AccountRequestModel
import com.memeusix.budgetbuddy.data.model.responseModel.AccountListModel
import com.memeusix.budgetbuddy.data.model.responseModel.AccountResponseModel
import com.memeusix.budgetbuddy.data.repository.AccountRepository
import com.memeusix.budgetbuddy.utils.SpUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val spUtils: SpUtils
) : ViewModel(), BaseViewModel {

    /**
     * Get All Accounts
     */
    private val _getAllAccounts =
        MutableStateFlow<ApiResponse<List<AccountResponseModel>>>(
            ApiResponse.Idle
        )
    val getAllAccounts = _getAllAccounts.asStateFlow()

    init {
        loadAccounts()
    }

    private fun loadAccounts() {
        if (spUtils.accountData != null && spUtils.accountData?.accounts?.isNotEmpty() == true) {
            _getAllAccounts.value = ApiResponse.Success(spUtils.accountData?.accounts)
        } else {
            getAllAccounts()
        }
    }

    fun getAllAccounts() {
        viewModelScope.launch {
            _getAllAccounts.value = ApiResponse.Loading(
                currentData = getAllAccounts.value.data
            )
            val response = handleData(_getAllAccounts.value) { accountRepository.getAllAccounts() }
            if (response is ApiResponse.Success) {
                spUtils.accountData = AccountListModel(response.data.orEmpty())
            }
            _getAllAccounts.value = response
        }
    }


    /**
     * Create Account Api
     */
    private val _createAccount =
        MutableStateFlow<ApiResponse<AccountResponseModel>>(ApiResponse.Idle)
    val createAccount: StateFlow<ApiResponse<AccountResponseModel>> get() = _createAccount
    fun createAccount(accountRequestModel: AccountRequestModel) {
        viewModelScope.launch {
            _createAccount.value = ApiResponse.Loading()
            _createAccount.value = accountRepository.createAccount(accountRequestModel)
            delay(500)
            _createAccount.value = ApiResponse.Idle
        }
    }

    /**
     * Update Account Api
     */
    private val _updateAccount =
        MutableStateFlow<ApiResponse<AccountResponseModel>>(ApiResponse.Idle)
    val updateAccount: StateFlow<ApiResponse<AccountResponseModel>> get() = _updateAccount
    fun updateAccount(accountId: Int, accountRequestModel: AccountRequestModel) {
        viewModelScope.launch {
            _updateAccount.value = ApiResponse.Loading()
            _updateAccount.value = accountRepository.updateAccount(accountId, accountRequestModel)
            delay(500)
            _updateAccount.value = ApiResponse.Idle

        }
    }

    /**
     * Delete Account Api
     */
    private val _deleteAccount =
        MutableStateFlow<ApiResponse<AccountResponseModel>>(ApiResponse.Idle)
    val deleteAccount: StateFlow<ApiResponse<AccountResponseModel>> get() = _deleteAccount
    fun deleteAccount(accountId: Int) {
        viewModelScope.launch {
            _deleteAccount.value = ApiResponse.Loading()
            _deleteAccount.value = accountRepository.deleteAccount(accountId)
            delay(500)
            _deleteAccount.value = ApiResponse.Idle
        }
    }


}
package com.memeusix.budgetbuddy.ui.dashboard.transactions.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.memeusix.budgetbuddy.data.ApiResponse
import com.memeusix.budgetbuddy.data.BaseViewModel
import com.memeusix.budgetbuddy.data.model.requestModel.TransactionPaginationRequestModel
import com.memeusix.budgetbuddy.data.model.requestModel.TransactionRequestModel
import com.memeusix.budgetbuddy.data.model.responseModel.AttachmentResponseModel
import com.memeusix.budgetbuddy.data.model.responseModel.TransactionResponseModel
import com.memeusix.budgetbuddy.data.repository.TransactionRepository
import com.memeusix.budgetbuddy.ui.dashboard.transactions.data.FilterOptionProvider
import com.memeusix.budgetbuddy.ui.dashboard.transactions.data.SelectedFilterModel
import com.memeusix.budgetbuddy.utils.SpUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    val spUtils: SpUtils
) : ViewModel(), BaseViewModel {

    // Create Transaction
    private val _createTransaction =
        MutableStateFlow<ApiResponse<TransactionResponseModel>>(ApiResponse.Idle)
    val createTransaction = _createTransaction.asStateFlow()
    fun createTransaction(transactionRequestModel: TransactionRequestModel) {
        viewModelScope.launch {
            _createTransaction.value = ApiResponse.Loading()
            _createTransaction.value =
                transactionRepository.createTransaction(transactionRequestModel)
            delay(500)
            _createTransaction.value = ApiResponse.Idle
        }
    }

    // Update Transaction
    private val _updateTransaction =
        MutableStateFlow<ApiResponse<TransactionResponseModel>>(ApiResponse.Idle)
    val updateTransaction = _updateTransaction.asStateFlow()
    fun updateTransaction(transactionId: Int, transactionRequestModel: TransactionRequestModel) {
        viewModelScope.launch {
            _updateTransaction.value = ApiResponse.Loading()
            _updateTransaction.value =
                transactionRepository.updateTransaction(transactionId, transactionRequestModel)
            delay(500)
            _updateTransaction.value = ApiResponse.Idle
        }
    }

    // Delete Transaction
    private val _deleteTransaction =
        MutableStateFlow<ApiResponse<TransactionResponseModel>>(ApiResponse.Idle)
    val deleteTransaction = _deleteTransaction.asStateFlow()
    fun deleteTransaction(transactionId: Int) {
        viewModelScope.launch {
            _deleteTransaction.value = ApiResponse.Loading()
            _deleteTransaction.value = transactionRepository.deleteTransaction(transactionId)
            delay(500)
            _deleteTransaction.value = ApiResponse.Idle
        }
    }

    // Upload Attachment
    private val _uploadAttachment =
        MutableStateFlow<ApiResponse<AttachmentResponseModel>>(ApiResponse.Idle)
    val uploadAttachment = _uploadAttachment.asStateFlow()
    fun uploadAttachment(imageUri: Uri) {
        viewModelScope.launch {
            _uploadAttachment.value = ApiResponse.Loading()
            _uploadAttachment.value = transactionRepository.uploadAttachment(imageUri)
        }
    }

    // handle filter state

    // Filter Screen States
    private val _getFilterOptions = MutableStateFlow(FilterOptionProvider.getFilterOptions())
    val getFilterOptions = _getFilterOptions.asStateFlow()
    fun initialize(spUtils: SpUtils) {
        FilterOptionProvider.initialize(spUtils)
        _getFilterOptions.value = FilterOptionProvider.getFilterOptions()
    }

    private val _selectedFilterState = MutableStateFlow<SelectedFilterModel?>(SelectedFilterModel())
    val selectedFilterModel = _selectedFilterState.asStateFlow()
    fun updateSelectedFilter(selectedFilterModel: SelectedFilterModel) {
        _selectedFilterState.value = selectedFilterModel
    }


    private val _filterState = MutableStateFlow(TransactionPaginationRequestModel())
    val filterState = _filterState.asStateFlow()

    fun updateFilter(newFilter: TransactionPaginationRequestModel) {
        _filterState.value = newFilter
    }

    // get paging data
    @OptIn(ExperimentalCoroutinesApi::class)
    private val _transactions: Flow<PagingData<TransactionResponseModel>> = _filterState
        .flatMapLatest { filter ->
            transactionRepository.getTransactions(filter)
        }
        .cachedIn(viewModelScope)

    fun getTransactions(): Flow<PagingData<TransactionResponseModel>> = _transactions

    // Handle Details Dialog state
    private val _transactionDetails = MutableStateFlow<TransactionResponseModel?>(null)
    val transactionDetails = _transactionDetails.asStateFlow()
    fun openDialog(details: TransactionResponseModel?) {
        _transactionDetails.value = details
    }

    fun closeDialog() {
        _transactionDetails.value = null
    }


}


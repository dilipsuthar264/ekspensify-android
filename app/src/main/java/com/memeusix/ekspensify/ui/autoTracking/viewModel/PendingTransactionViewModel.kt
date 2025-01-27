package com.memeusix.ekspensify.ui.autoTracking.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.memeusix.ekspensify.data.model.responseModel.AccountResponseModel
import com.memeusix.ekspensify.room.model.PendingTransactionModel
import com.memeusix.ekspensify.room.repository.PendingTransactionRepo
import com.memeusix.ekspensify.ui.acounts.data.BankModel
import com.memeusix.ekspensify.utils.spUtils.SpUtilsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PendingTransactionViewModel @Inject constructor(
    private val spUtilsManager: SpUtilsManager,
    private val pendingTransactionRepo: PendingTransactionRepo
) : ViewModel() {


    val accounts = spUtilsManager.accountData.value?.accounts

    fun getAccountModel(accountName: String?): AccountResponseModel? {
        val bankModel = BankModel.getBanks()
            .find { it.name.uppercase() == accountName?.uppercase() || it.shortName.uppercase() == accountName?.uppercase() }
        return accounts?.find { it.icon == bankModel?.iconSlug }
    }


    private val pendingTransactions: Flow<PagingData<PendingTransactionModel>> =
        pendingTransactionRepo.getPendingTransaction().cachedIn(viewModelScope)

    fun getPendingTransactions(): Flow<PagingData<PendingTransactionModel>> = pendingTransactions


    // delete Transaction
    fun deletePendingTransaction(id: Int) {
        viewModelScope.launch {
            val response = pendingTransactionRepo.deletePendingTransaction(id)
            response.fold(onFailure = {}, onSuccess = {
                pendingTransactions.retry()
            })
        }
    }


}
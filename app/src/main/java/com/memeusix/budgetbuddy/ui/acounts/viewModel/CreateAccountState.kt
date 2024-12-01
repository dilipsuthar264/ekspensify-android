package com.memeusix.budgetbuddy.ui.acounts.viewModel

import androidx.lifecycle.ViewModel
import com.memeusix.budgetbuddy.data.model.responseModel.AccountResponseModel
import com.memeusix.budgetbuddy.ui.acounts.data.BankModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CreateAccountState @Inject constructor() : ViewModel() {

    private val _walletList = MutableStateFlow(BankModel.getWallet())
    val walletList = _walletList.asStateFlow()

    private val _bankList = MutableStateFlow(BankModel.getBanks())
    val bankList = _bankList.asStateFlow()

    private val _selectedBank = MutableStateFlow<BankModel?>(null)
    val selectedBank = _selectedBank.asStateFlow()

    private val _selectedWallet = MutableStateFlow<BankModel?>(null)
    val selectedWallet = _selectedWallet.asStateFlow()


    fun initialize(
        accountLists: List<AccountResponseModel>?,
        argsAccountDetails: AccountResponseModel?
    ): Pair<BankModel?, BankModel?> {
        val accountIconSlugSet = accountLists?.map { it.icon }?.toSet() ?: emptySet()

        argsAccountDetails?.let { details ->
            val selectedWallet = _walletList.value.find { it.iconSlug == details.icon }
            val selectedBank = _bankList.value.find { it.iconSlug == details.icon }
            _selectedBank.value = selectedBank
            _selectedWallet.value = selectedWallet
        }

        val updateList: (List<BankModel>) -> Unit = { list ->
            list.forEach {
                it.isEnable =
                    it.iconSlug !in accountIconSlugSet || (it == _selectedBank.value) || (it == _selectedWallet.value)
            }
        }
        updateList(_walletList.value)
        updateList(_bankList.value)

        if (argsAccountDetails == null) {
            val defaultWallet = _walletList.value.firstOrNull { it.isEnable }
            val defaultBank = _bankList.value.firstOrNull { it.isEnable }
            _selectedBank.value = defaultBank
            _selectedWallet.value = defaultWallet
        }
        return Pair(_selectedBank.value, _selectedWallet.value)
    }
}
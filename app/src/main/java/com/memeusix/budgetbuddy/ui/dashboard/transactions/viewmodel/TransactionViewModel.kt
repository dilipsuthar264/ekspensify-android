package com.memeusix.budgetbuddy.ui.dashboard.transactions.viewmodel

import androidx.lifecycle.ViewModel
import com.memeusix.budgetbuddy.data.BaseViewModel
import com.memeusix.budgetbuddy.utils.SpUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
     val spUtils: SpUtils
) : ViewModel(), BaseViewModel {

}
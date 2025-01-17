package com.memeusix.budgetbuddy.ui.autoTracking.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.memeusix.budgetbuddy.room.model.PendingTransactionModel
import com.memeusix.budgetbuddy.room.repository.PendingTransactionRepo
import com.memeusix.budgetbuddy.utils.smsReceiver.SmsHelper.updateReceiverState
import com.memeusix.budgetbuddy.utils.spUtils.SpUtilsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AutoTrackViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    val spUtilsManager: SpUtilsManager,
    private val pendingTransactionRepo: PendingTransactionRepo
) : ViewModel() {

    private val _isSmsFeatureEnable =
        MutableStateFlow<Boolean>(spUtilsManager.isAutoTrackingEnable.value)
    val isSmsFeatureEnable = _isSmsFeatureEnable.asStateFlow()

    fun toggleSmsFeature(enable: Boolean) {
        updateReceiverState(context, enable)
        _isSmsFeatureEnable.value = enable
        spUtilsManager.updateAutoTracking(enable)
    }

}


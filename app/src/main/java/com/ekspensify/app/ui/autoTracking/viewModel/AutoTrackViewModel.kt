package com.ekspensify.app.ui.autoTracking.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.ekspensify.app.room.repository.PendingTransactionRepo
import com.ekspensify.app.utils.smsReceiver.SmsHelper.updateReceiverState
import com.ekspensify.app.utils.spUtils.SpUtilsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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


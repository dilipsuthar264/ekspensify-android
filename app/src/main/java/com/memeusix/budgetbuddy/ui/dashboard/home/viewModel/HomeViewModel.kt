package com.memeusix.budgetbuddy.ui.dashboard.home.viewModel

import androidx.lifecycle.ViewModel
import com.memeusix.budgetbuddy.utils.spUtils.SpUtilsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val spUtilsManager: SpUtilsManager
) : ViewModel() {

}
package com.memeusix.ekspensify.ui.dashboard.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memeusix.ekspensify.data.ApiResponse
import com.memeusix.ekspensify.data.BaseViewModel
import com.memeusix.ekspensify.data.model.requestModel.InsightsQueryModel
import com.memeusix.ekspensify.data.model.responseModel.AcSummaryResponseModel
import com.memeusix.ekspensify.data.model.responseModel.CategoryInsightsResponseModel
import com.memeusix.ekspensify.data.repository.AccountRepository
import com.memeusix.ekspensify.data.repository.CategoryRepository
import com.memeusix.ekspensify.utils.spUtils.SpUtilsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val spUtilsManager: SpUtilsManager,
    val categoryRepository: CategoryRepository,
    val accountRepository: AccountRepository
) : ViewModel(), BaseViewModel {

    private val _insightsQueries = MutableStateFlow(InsightsQueryModel())
    val insightsQueries = _insightsQueries.asStateFlow()
    fun setInsightsQuery(query: InsightsQueryModel) {
        _insightsQueries.value = query
    }

    /**
     * get category insights
     */
    private val _getCategoryInsight: MutableStateFlow<ApiResponse<List<CategoryInsightsResponseModel>>> =
        MutableStateFlow(ApiResponse.idle())
    val getCategoryInsight = _getCategoryInsight.asStateFlow()
    fun getCategoryInsights() {
        viewModelScope.launch {
            _getCategoryInsight.value = ApiResponse.loading(_getCategoryInsight.value.data)
            _getCategoryInsight.value =
                handleData(_getCategoryInsight.value) {
                    categoryRepository.getCategoryInsights(_insightsQueries.value)
                }
        }
    }


    /**
     * get Account Summery
     */
    private val _getAcSummary =
        MutableStateFlow<ApiResponse<AcSummaryResponseModel>>(ApiResponse.idle())
    val getSummary = _getAcSummary.asStateFlow()
    fun getAcSummary() {
        viewModelScope.launch {
            _getAcSummary.value = ApiResponse.loading(_getAcSummary.value.data)
            delay(1000)
            _getAcSummary.value = handleData(_getAcSummary.value) {
                accountRepository.getSummary(_insightsQueries.value)
            }
        }
    }

    fun fetchDashBoardInsights() {
        getAcSummary()
        getCategoryInsights()
    }


    init {
        fetchDashBoardInsights()
    }
}
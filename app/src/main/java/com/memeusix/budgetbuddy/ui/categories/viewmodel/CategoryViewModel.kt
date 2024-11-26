package com.memeusix.budgetbuddy.ui.categories.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memeusix.budgetbuddy.data.ApiResponse
import com.memeusix.budgetbuddy.data.model.responseModel.CategoryResponseModel
import com.memeusix.budgetbuddy.data.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    /**
     * get Categories
     */
    private val _getCategories =
        MutableStateFlow<ApiResponse<List<CategoryResponseModel>>>(ApiResponse.Idle)
    val getCategories = _getCategories.asStateFlow()
        .onStart { getCategories() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = _getCategories.value
        )

    fun getCategories() {
        viewModelScope.launch {
            _getCategories.value = ApiResponse.Loading(
                currentData = _getCategories.value.data,
                currentError = null
            )
            _getCategories.value = categoryRepository.getCategories()
        }
    }

    /**
     * create Categories
     */
    private val _createCategory =
        MutableStateFlow<ApiResponse<CategoryResponseModel>>(ApiResponse.Idle)
    val createCategory = _createCategory.asStateFlow()
    fun createCategory(categoryResponseModel: CategoryResponseModel) {
        viewModelScope.launch {
            _createCategory.value = ApiResponse.Loading()
            _createCategory.value = categoryRepository.createCategory(categoryResponseModel)
            delay(500)
            _createCategory.value = ApiResponse.Idle
        }
    }

    /**
     * delete Categories
     */
    private val _deleteCategory: MutableStateFlow<ApiResponse<CategoryResponseModel>> =
        MutableStateFlow(ApiResponse.Idle)
    val deleteCategory = _deleteCategory.asStateFlow()
    fun deleteCategory(categoryId: Int) {
        viewModelScope.launch {
            _deleteCategory.value = ApiResponse.Loading()
            _deleteCategory.value = categoryRepository.deleteCategory(categoryId)
            delay(500)
            _deleteCategory.value = ApiResponse.Idle
        }
    }

    fun removeDeletedFromList(categoryResponseModel: CategoryResponseModel) {
        val currentList = _getCategories.value.data?.toMutableList()
        currentList?.removeIf { it.id == categoryResponseModel.id }
        _getCategories.value = ApiResponse.Success(currentList)
    }


}
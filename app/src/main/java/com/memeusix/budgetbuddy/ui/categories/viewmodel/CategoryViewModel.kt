package com.memeusix.budgetbuddy.ui.categories.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memeusix.budgetbuddy.data.ApiResponse
import com.memeusix.budgetbuddy.data.BaseViewModel
import com.memeusix.budgetbuddy.data.model.responseModel.CategoryListModel
import com.memeusix.budgetbuddy.data.model.responseModel.CategoryResponseModel
import com.memeusix.budgetbuddy.data.model.responseModel.CustomIconModel
import com.memeusix.budgetbuddy.data.repository.CategoryRepository
import com.memeusix.budgetbuddy.utils.SpUtils
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
    private val categoryRepository: CategoryRepository,
    val spUtils: SpUtils
) : ViewModel(), BaseViewModel {

    /**
     * get Categories
     */
    private val _getCategories =
        MutableStateFlow<ApiResponse<List<CategoryResponseModel>>>(ApiResponse.Idle)
    val getCategories = _getCategories.asStateFlow()

    init {
        loadCategoriesFromSpUtils()
    }

    private fun loadCategoriesFromSpUtils() {
        spUtils.categoriesData?.categories?.takeIf { it.isNotEmpty() }?.let {
            _getCategories.value = ApiResponse.Success(it)
        } ?: run {
            getCategories()
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            _getCategories.value = ApiResponse.Loading(
                currentData = _getCategories.value.data,
            )
            val response = handleData(_getCategories.value) { categoryRepository.getCategories() }
            if (response is ApiResponse.Success) {
                spUtils.categoriesData = CategoryListModel(categories = response.data.orEmpty())
            }
            _getCategories.value = response
        }
    }

    /**
     * get category icons
     */
    private val _getCustomIcons =
        MutableStateFlow<ApiResponse<List<CustomIconModel>>>(ApiResponse.Idle)
    val getCustomIcons = _getCustomIcons.asStateFlow()
        .onStart { getCustomIcons() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(10000),
            ApiResponse.Idle
        )

    fun getCustomIcons() {
        viewModelScope.launch {
            _getCustomIcons.value = ApiResponse.Loading(
                currentData = _getCustomIcons.value.data,
            )
            _getCustomIcons.value =
                handleData(_getCustomIcons.value) { categoryRepository.getCustomIcons() }
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
package com.memeusix.budgetbuddy.ui.dashboard.transactions.data

import com.memeusix.budgetbuddy.data.model.responseModel.AccountResponseModel
import com.memeusix.budgetbuddy.data.model.responseModel.CategoryResponseModel
import com.memeusix.budgetbuddy.utils.SpUtils
import com.memeusix.budgetbuddy.utils.TransactionType


object FilterOptionProvider {
    // Late-initialized values for dynamic options
    private var categories: List<CategoryResponseModel>? = null
    private var accounts: List<AccountResponseModel>? = null

    fun initialize(spUtils: SpUtils) {
        categories = spUtils.categoriesData?.categories
        accounts = spUtils.accountData?.accounts
    }

    fun getFilterOptions() = listOf(
        FilterOptions(
            "Type",
            TransactionType.entries,
            isMultiSelection = false,
            listType = FilterType.TYPE
        ),
        FilterOptions(
            "Amount",
            AmountRange.entries,
            isMultiSelection = false,
            listType = FilterType.AMT_RANGE
        ),
        FilterOptions(
            "Account",
            accounts.orEmpty(),
            isMultiSelection = true,
            listType = FilterType.ACCOUNTS
        ),
        FilterOptions(
            "Category",
            categories.orEmpty(),
            isMultiSelection = true,
            listType = FilterType.CATEGORIES
        ),
        FilterOptions(
            "Time Period",
            DateRange.entries,
            isMultiSelection = false,
            listType = FilterType.DATE_RANGE
        ),
        FilterOptions(
            "Sort By",
            SortBy.entries,
            isMultiSelection = false,
            listType = FilterType.SORT_BY
        )
    )
}


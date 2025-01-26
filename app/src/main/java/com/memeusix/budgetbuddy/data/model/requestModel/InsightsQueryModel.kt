package com.memeusix.budgetbuddy.data.model.requestModel

import com.memeusix.budgetbuddy.ui.dashboard.transactions.data.DateRange
import com.memeusix.budgetbuddy.utils.CategoryType

data class InsightsQueryModel(
    val type: CategoryType = CategoryType.DEBIT,
    val dateRange: DateRange = DateRange.THIS_WEEK
)
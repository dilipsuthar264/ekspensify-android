package com.memeusix.ekspensify.data.model.requestModel

import com.memeusix.ekspensify.ui.dashboard.transactions.data.DateRange
import com.memeusix.ekspensify.utils.CategoryType

data class InsightsQueryModel(
    val type: CategoryType = CategoryType.DEBIT,
    val dateRange: DateRange = DateRange.THIS_WEEK
)
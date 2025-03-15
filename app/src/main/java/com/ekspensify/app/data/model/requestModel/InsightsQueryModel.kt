package com.ekspensify.app.data.model.requestModel

import com.ekspensify.app.ui.dashboard.transactions.data.DateRange
import com.ekspensify.app.utils.CategoryType

data class InsightsQueryModel(
    val type: CategoryType = CategoryType.DEBIT,
    val dateRange: DateRange = DateRange.THIS_WEEK
)
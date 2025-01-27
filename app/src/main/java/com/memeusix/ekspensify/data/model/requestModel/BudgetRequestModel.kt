package com.memeusix.ekspensify.data.model.requestModel

import com.google.gson.annotations.SerializedName
import com.memeusix.ekspensify.utils.BudgetPeriod
import com.memeusix.ekspensify.utils.BudgetType
import java.io.Serializable
import java.time.LocalDate

data class BudgetRequestModel(
    @SerializedName("limit")
    val limit: Int = 0,

    @SerializedName("account_ids")
    val accountIds: List<Int>? = null,

    @SerializedName("category_ids")
    val categoryIds: List<Int>? = null,

    @SerializedName("period")
    val period: String = BudgetPeriod.MONTHLY.name,

    @SerializedName("type")
    val type: String = BudgetType.RECURRING.name,

    @SerializedName("start_date")
    val startDate: String = LocalDate.now().toString(),

    @SerializedName("end_date")
    val endDate: String? = null
) : Serializable

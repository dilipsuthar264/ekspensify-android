package com.ekspensify.app.data.model.responseModel

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BudgetReportResponseModel(
    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("amount")
    var amount: Double? = null,

    @SerializedName("period_no")
    var periodNo: Int? = null,

    @SerializedName("total_transactions")
    var totalTransactions: Double? = null,

    @SerializedName("period_start_date")
    var periodStartDate: String? = null,

    @SerializedName("period_end_date")
    var periodEndDate: String? = null,

    @SerializedName("transactions")
    var transactions: List<TransactionResponseModel> = emptyList()
) : Serializable
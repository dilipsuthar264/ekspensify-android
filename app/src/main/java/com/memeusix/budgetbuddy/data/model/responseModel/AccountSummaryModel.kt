package com.memeusix.budgetbuddy.data.model.responseModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AcSummaryResponseModel(
    @SerializedName("total")
    @Expose
    var total: Int? = null,
    @SerializedName("credit")
    @Expose
    var credit: Int? = null,

    @SerializedName("debit")
    @Expose
    var debit: Int? = null
) : Serializable
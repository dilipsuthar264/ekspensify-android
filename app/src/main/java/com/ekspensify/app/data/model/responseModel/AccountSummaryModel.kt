package com.ekspensify.app.data.model.responseModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AcSummaryResponseModel(
    @SerializedName("total")
    @Expose
    var total: Double? = null,
    @SerializedName("credit")
    @Expose
    var credit: Double? = null,

    @SerializedName("debit")
    @Expose
    var debit: Double? = null
) : Serializable
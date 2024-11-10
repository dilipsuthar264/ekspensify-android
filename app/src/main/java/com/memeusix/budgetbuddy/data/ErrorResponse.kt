package com.memeusix.budgetbuddy.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ErrorResponseModel(
    @SerializedName("code") @Expose var code: Int? = null,

    @SerializedName("message") @Expose var message: String? = null,

    @SerializedName("details") @Expose var details: List<ErrorDetails>? = null
) : Parcelable


@Parcelize
data class ErrorDetails(
    @SerializedName("field") @Expose var field: String? = null,

    @SerializedName("message") @Expose var message: List<String>? = null
) : Parcelable

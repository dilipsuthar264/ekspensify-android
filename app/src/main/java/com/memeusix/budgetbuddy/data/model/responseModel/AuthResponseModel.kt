package com.memeusix.budgetbuddy.data.model.responseModel

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthResponseModel(
    @SerializedName("token")
    @Expose
    var token: String? = null,

    @SerializedName("user")
    @Expose
    var user: UserResponseModel? = null,
) : Parcelable
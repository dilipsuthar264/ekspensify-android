package com.memeusix.budgetbuddy.data.model.responseModel

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponseModel(
    @SerializedName("id")
    @Expose
    var id: String? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("email")
    @Expose
    var email: String? = null,

    @SerializedName("role")
    @Expose
    var role: String? = null,

    @SerializedName("status")
    @Expose
    var status: String? = null,

    @SerializedName("is_verified")
    @Expose
    var isVerified: Boolean? = null,

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null,

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null,
) : Parcelable
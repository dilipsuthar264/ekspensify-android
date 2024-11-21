package com.memeusix.budgetbuddy.data.model.responseModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@kotlinx.serialization.Serializable
data class AccountResponseModel(
    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("type")
    @Expose
    var type: String? = null,

    @SerializedName("slug")
    @Expose
    var slug: String? = null,

    @SerializedName("icon")
    @Expose
    var icon: String? = null,

    @SerializedName("balance")
    @Expose
    var balance: Int? = null,

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null,

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null,

    @SerializedName("deleted_at")
    @Expose
    var deletedAt: String? = null
) : Serializable

@kotlinx.serialization.Serializable
data class AccountListModel(
    val accounts: List<AccountResponseModel>
) : Serializable
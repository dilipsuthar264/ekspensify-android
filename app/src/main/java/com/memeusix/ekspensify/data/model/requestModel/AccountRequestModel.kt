package com.memeusix.ekspensify.data.model.requestModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AccountRequestModel(
    @SerializedName("name")
    @Expose
    val name: String? = null,

    @SerializedName("type")
    @Expose
    val type: String? = null,

    @SerializedName("balance")
    @Expose
    val balance: Int? = null,
) : Serializable

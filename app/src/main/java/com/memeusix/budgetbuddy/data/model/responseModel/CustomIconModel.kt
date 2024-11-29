package com.memeusix.budgetbuddy.data.model.responseModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CustomIconModel(
    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("path")
    @Expose
    var path: String? = null,

    @SerializedName("mime")
    @Expose
    var mime: String? = null,

    @SerializedName("type")
    @Expose
    var type: String? = null,

    @SerializedName("size")
    @Expose
    var size: Int? = null,

    @SerializedName("collection")
    @Expose
    var collection: String? = null,

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
package com.memeusix.budgetbuddy.data.model

data class TextFieldStateModel(
    var value: String = "",
    var error: String? = null,
) {
    fun isValid(): Boolean = error == null
}
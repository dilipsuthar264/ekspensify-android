package com.memeusix.budgetbuddy.data.model

data class TextFieldStateModel(
    var text: String = "",
    var error: String? = null,
) {
    fun isValid(): Boolean = error == null
}
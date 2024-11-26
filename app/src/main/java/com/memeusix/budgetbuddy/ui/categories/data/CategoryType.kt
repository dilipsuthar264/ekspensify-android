package com.memeusix.budgetbuddy.ui.categories.data

enum class CategoryType {
    INCOME,
    EXPENSE,
    BOTH;

    fun getValue(): String? {
        return when (this) {
            INCOME -> "DEBIT"
            EXPENSE -> "CREDIT"
            BOTH -> null
        }
    }

    fun getDisplayName(): String {
        return when (this) {
            INCOME -> "Income"
            EXPENSE -> "Expense"
            BOTH -> "Both"
        }
    }
}

fun String?.getCategoryType(): String {
    return when (this) {
        "DEBIT" -> "Income"
        "CREDIT" -> "Expense"
        else -> "Both"
    }
}


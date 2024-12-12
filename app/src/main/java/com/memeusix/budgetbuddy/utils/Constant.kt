package com.memeusix.budgetbuddy.utils

enum class AccountType {
    BANK,
    WALLET;

    fun getDisplayName(): String {
        return when (this) {
            WALLET -> "Wallet"
            BANK -> "Bank"
        }
    }
}

object NavigationRequestKeys {
    const val DELETE_OR_UPDATE_ACCOUNT = "DELETE_OR_UPDATE_ACCOUNT"
    const val CREATE_CATEGORY = "CREATE_CATEGORY"
    const val REFRESH_TRANSACTION = "REFRESH_TRANSACTION"
}

enum class TransactionType(val displayName : String) {
    DEBIT("Expense"),
    CREDIT("Income");

    fun getInvertedType(): TransactionType = when (this) {
        DEBIT -> CREDIT
        CREDIT -> DEBIT
    }
}


enum class BottomSheetType {
    CATEGORY,
    ACCOUNT;

    fun getDisplayName() = when (this) {
        CATEGORY -> "Category"
        ACCOUNT -> "Account"
    }
}

object ErrorReason {
    const val CUSTOM_CATEGORY_ICON_NOT_FOUND = "CUSTOM_CATEGORY_ICON_NOT_FOUND"
}
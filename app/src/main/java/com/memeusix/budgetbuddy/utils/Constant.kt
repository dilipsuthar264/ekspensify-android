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
}

enum class TransactionType {
    DEBIT,
    CREDIT,
    TRANSFER;
}

enum class BottomSheetType {
    CATEGORY,
    ACCOUNT;

    fun getDisplayName() = when (this) {
        CATEGORY -> "Category"
        ACCOUNT -> "Account"
    }
}
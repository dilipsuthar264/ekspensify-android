package com.memeusix.budgetbuddy.utils


enum class AccountType {
    WALLET,
    BANK;

    fun getVal(): String {
        return when (this) {
            WALLET -> "Wallet"
            BANK -> "Bank"
        }
    }
}

object NavigationRequestKeys{
    const val DELETE_OR_UPDATE_ACCOUNT =  "DELETE_OR_UPDATE_ACCOUNT"
}

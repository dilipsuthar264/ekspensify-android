package com.memeusix.budgetbuddy.utils

import androidx.compose.ui.graphics.Color
import com.memeusix.budgetbuddy.ui.theme.Light20
import com.memeusix.budgetbuddy.ui.theme.Teal

const val SPLASH_DURATION = 300L

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

enum class CategoryType(val displayName: String) {
    DEBIT("Expense"),
    CREDIT("Income"),
    BOTH("Both");

    fun getValue(): String? {
        return when (this) {
            BOTH -> null
            else -> this.name
        }
    }
}


object NavigationRequestKeys {
    const val DELETE_OR_UPDATE_ACCOUNT = "DELETE_OR_UPDATE_ACCOUNT"
    const val CREATE_CATEGORY = "CREATE_CATEGORY"
    const val REFRESH_TRANSACTION = "REFRESH_TRANSACTION"
}

enum class TransactionType(val displayName: String) {
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
    const val ICON_NOT_FOUND = "ICON_NOT_FOUND"
}

enum class BudgetType(val displayName: String, val description: String) {
    RECURRING(
        "Recurring Budget",
        "Recurring budgets renew on the first day of every monthly billing period."
    ),
    EXPIRING(
        "Expiring Budget",
        "Expiring daily budgets Stop renewing at the end Of the selected expiration date."
    )
}

enum class BudgetStatus(val displayName: String, val color: Color) {
    RUNNING("Running", Teal),
    CLOSED("Closed", Light20)
}

enum class DatePickerType() {
    START_DATE,
    END_DATE
}

enum class BudgetPeriod(val displayName: String) {
    DAILY("Daily"),
    WEEKLY("Weekly"),
    MONTHLY("Monthly"),
}

enum class BottomSheetSelectionType {
    SINGLE, MULTIPLE
}


object NotificationActivity {
    const val BUDGET = "BUDGET"
    const val TRANSACTION = "TRANSACTION"
}
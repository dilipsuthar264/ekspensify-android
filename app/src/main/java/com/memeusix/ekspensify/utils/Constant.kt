package com.memeusix.ekspensify.utils

import androidx.compose.ui.graphics.Color
import com.memeusix.ekspensify.ui.dashboard.transactions.data.Displayable
import com.memeusix.ekspensify.ui.theme.Light20
import com.memeusix.ekspensify.ui.theme.Teal

const val SPLASH_DURATION = 300L

object CommonData{
    const val PRIVACY_POLICY = "https://chemical-comma-538.notion.site/Privacy-Policy-191575538d2c80b9983ded1811775d3e"
    const val TERMS_AND_CONDITION = "https://chemical-comma-538.notion.site/Term-Condition-191575538d2c805d9e17f3e10eb3c8b1"
}

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

enum class CategoryType(override val displayName: String) : Displayable {
    DEBIT("Expense"),
    CREDIT("Income"),
    BOTH("Both");

    fun getValue(): String? {
        return when (this) {
            BOTH -> null
            else -> this.name
        }
    }

    companion object {
        fun getEntriesWithNoBoth(): List<CategoryType> {
            return entries.filter { it != BOTH }
        }
    }
}


object NavigationRequestKeys {
    const val DELETE_OR_UPDATE_ACCOUNT = "DELETE_OR_UPDATE_ACCOUNT"
    const val CREATE_CATEGORY = "CREATE_CATEGORY"
    const val REFRESH_TRANSACTION = "REFRESH_TRANSACTION"
}

enum class TransactionType(override val displayName: String) : Displayable {
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

enum class BudgetType(override val displayName: String, val description: String) : Displayable {
    RECURRING(
        "Recurring Budget",
        "Recurring budgets run continuously without an expiration date."
    ),
    EXPIRING(
        "Expiring Budget",
        "Expiring budgets stop renewing at the end of the selected expiration date."
    )
}

enum class BudgetStatus(override val displayName: String, val color: Color) : Displayable {
    RUNNING("Running", Teal),
    CLOSED("Closed", Light20)
}

enum class DatePickerType() {
    START_DATE,
    END_DATE
}

enum class BudgetPeriod(override val displayName: String) : Displayable {
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

enum class ExportFileFormat() {
    PDF, CSV
}
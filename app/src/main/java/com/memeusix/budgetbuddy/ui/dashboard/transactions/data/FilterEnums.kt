package com.memeusix.budgetbuddy.ui.dashboard.transactions.data

import android.annotation.SuppressLint
import java.time.LocalDate
import java.time.format.DateTimeFormatter

enum class DateRange(val displayText: String) {
    TODAY("Today"),
    YESTERDAY("Yesterday"),
    THIS_MONTH("This Month"),
    THIS_YEAR("This Year"),
    CUSTOM("Custom Range");
}

enum class AmountRange(val displayText: String, val amountRange: Pair<Int?, Int?>) {
    LESS_THAN_100("Less than ₹100", null to 100),
    BETWEEN_100_500("₹100 - ₹500", 100 to 500),
    BETWEEN_500_1000("₹500 - ₹1000", 500 to 1000),
    BETWEEN_1000_2000("₹1000 - ₹2000", 1000 to 2000),
    MORE_THAN_2000("More than ₹2000", 2000 to null),
    CUSTOM("Custom Range", null to null);
}

enum class SortBy(val displayText: String, val sortBy: String) {
    OLDEST_TO_NEWEST("Oldest to Newest", "created_at"),
    NEWEST_TO_OLDEST("Newest to Oldest", "-created_at"),
    AMOUNT_LOW_TO_HIGH("Amount Low to High", "amount"),
    AMOUNT_HIGH_TO_LOW("Amount High to Low", "-amount");
}

@SuppressLint("NewApi")
fun DateRange.getFormattedDateRange(): Pair<String?, String?> {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val today = LocalDate.now()
    val yesterday = today.minusDays(1)
    val startOfThisMonth = today.withDayOfMonth(1)
    val startOfThisYear = today.withDayOfYear(1)
    val startOfDay = today.atStartOfDay()
    val endOfDay = today.atTime(23, 59, 59)

    return when (this) {
        DateRange.TODAY -> Pair(startOfDay.format(formatter), endOfDay.format(formatter))
        DateRange.YESTERDAY -> Pair(
            yesterday.atStartOfDay().format(formatter),
            yesterday.atTime(23, 59, 59).format(formatter)
        )
        DateRange.THIS_MONTH -> Pair(
            startOfThisMonth.atStartOfDay().format(formatter),
            endOfDay.format(formatter)
        )
        DateRange.THIS_YEAR -> Pair(
            startOfThisYear.atStartOfDay().format(formatter),
            endOfDay.format(formatter)
        )
        DateRange.CUSTOM -> Pair(null, null)
    }
}
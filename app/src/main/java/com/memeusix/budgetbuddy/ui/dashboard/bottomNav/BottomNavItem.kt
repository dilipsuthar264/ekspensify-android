package com.memeusix.budgetbuddy.ui.dashboard.bottomNav

import com.memeusix.budgetbuddy.R

sealed class BottomNavItem(val icon: Int, val label: String) {
    data object Home : BottomNavItem(R.drawable.ic_home, "Home")
//    data object Transaction : BottomNavItem(R.drawable.ic_transaction, "Transaction")
    data object Action : BottomNavItem(R.drawable.ic_home, "Action")
//    data object Budget : BottomNavItem(R.drawable.ic_pie_chart, "Budget")
    data object Profile : BottomNavItem(R.drawable.ic_user, "Users")
}
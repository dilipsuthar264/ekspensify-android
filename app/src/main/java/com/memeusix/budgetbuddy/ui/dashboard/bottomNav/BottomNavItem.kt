package com.memeusix.budgetbuddy.ui.dashboard.bottomNav

import com.memeusix.budgetbuddy.R

sealed class BottomNavItem(val icon: Int, val selectedIcon: Int, val label: String) {
    data object Home : BottomNavItem(R.drawable.ic_home_new, R.drawable.ic_home_new_fill, "Home")

    data object Transaction : BottomNavItem(
        R.drawable.ic_transaction_new, R.drawable.ic_transaction_new_fill, "Transaction"
    )

    data object Action : BottomNavItem(R.drawable.ic_home, R.drawable.ic_home_new_fill, "Action")

    data object Budget :
        BottomNavItem(R.drawable.ic_budget_new, R.drawable.ic_budget_new_fill, "Budget")

    data object Profile :
        BottomNavItem(R.drawable.ic_user_new, R.drawable.ic_user_new_fill, "Profile")
}

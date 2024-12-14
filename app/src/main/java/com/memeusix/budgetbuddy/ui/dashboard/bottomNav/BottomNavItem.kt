package com.memeusix.budgetbuddy.ui.dashboard.bottomNav

import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.navigation.BudgetsScreenRoute
import com.memeusix.budgetbuddy.navigation.HomeScreenRoute
import com.memeusix.budgetbuddy.navigation.ProfileScreenRoute
import com.memeusix.budgetbuddy.navigation.TransactionsScreenRoute

sealed class BottomNavItem(
    val icon: Int,
    val selectedIcon: Int,
    val label: String,
    val route: Any?
) {
    data object Home :
        BottomNavItem(R.drawable.ic_home_new, R.drawable.ic_home_new_fill, "Home", HomeScreenRoute)

    data object Transaction : BottomNavItem(
        R.drawable.ic_transaction_new,
        R.drawable.ic_transaction_new_fill,
        "Transactions",
        TransactionsScreenRoute
    )

    data object Action :
        BottomNavItem(R.drawable.ic_home, R.drawable.ic_home_new_fill, "Action", null)

    data object Budget :
        BottomNavItem(
            R.drawable.ic_budget_new,
            R.drawable.ic_budget_new_fill,
            "Budget",
            BudgetsScreenRoute
        )

    data object Profile :
        BottomNavItem(
            R.drawable.ic_user_new,
            R.drawable.ic_user_new_fill,
            "Profile",
            ProfileScreenRoute
        )
}

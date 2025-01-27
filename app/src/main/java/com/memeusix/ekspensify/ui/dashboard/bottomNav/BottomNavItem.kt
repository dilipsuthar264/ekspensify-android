package com.memeusix.ekspensify.ui.dashboard.bottomNav

import com.memeusix.ekspensify.R
import com.memeusix.ekspensify.navigation.BudgetsScreenRoute
import com.memeusix.ekspensify.navigation.HomeScreenRoute
import com.memeusix.ekspensify.navigation.ProfileScreenRoute
import com.memeusix.ekspensify.navigation.TransactionsScreenRoute

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
        "Transaction",
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
            R.drawable.ic_profile_new,
            R.drawable.ic_profile_new_fill,
            "Profile",
            ProfileScreenRoute
        )
}

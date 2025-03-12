package com.ekspensify.app.ui.dashboard.bottomNav

import com.ekspensify.app.R
import com.ekspensify.app.navigation.BudgetsScreenRoute
import com.ekspensify.app.navigation.HomeScreenRoute
import com.ekspensify.app.navigation.ProfileScreenRoute
import com.ekspensify.app.navigation.TransactionsScreenRoute

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

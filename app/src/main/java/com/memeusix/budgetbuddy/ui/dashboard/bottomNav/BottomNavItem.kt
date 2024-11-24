package com.memeusix.budgetbuddy.ui.dashboard.bottomNav

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.ui.theme.Dark10
import com.memeusix.budgetbuddy.ui.theme.Light100

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

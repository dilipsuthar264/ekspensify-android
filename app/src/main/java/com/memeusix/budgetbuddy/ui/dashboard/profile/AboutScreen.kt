package com.memeusix.budgetbuddy.ui.dashboard.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.components.AppBar
import com.memeusix.budgetbuddy.components.CustomListItem
import com.memeusix.budgetbuddy.ui.dashboard.budget.components.CreateBudgetSectionCard
import com.memeusix.budgetbuddy.ui.dashboard.profile.data.AboutOptions
import com.memeusix.budgetbuddy.ui.theme.extendedColors
import com.memeusix.budgetbuddy.utils.dynamicImePadding

@Composable
fun AboutScreen(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            AppBar(
                heading = "About",
                navController
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .dynamicImePadding(paddingValues)
                .padding(horizontal = 20.dp)
        ) {
            CreateBudgetSectionCard("") {
                AboutOptions.entries.forEach {
                    CustomListItem(
                        title = it.title,
                        modifier = Modifier.padding(vertical = 9.dp),
                        trailingContent = {
                            Icon(
                                painter = painterResource(R.drawable.ic_arrow_right),
                                tint = MaterialTheme.extendedColors.iconColor,
                                contentDescription = null,
                            )
                        },
                        onClick = {}
                    )
                }
            }
        }
    }
}
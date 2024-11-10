package com.memeusix.budgetbuddy.ui.dashboard.transactions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.memeusix.budgetbuddy.navigation.TransactionScreenRoute
import com.memeusix.budgetbuddy.ui.components.AppBar
import com.memeusix.budgetbuddy.ui.theme.Light100

@Composable
fun TransactionScreen(
    navController: NavController,
    args: TransactionScreenRoute,
) {

    Scaffold(
        topBar = {
            AppBar(
                heading = if (args.userId != null) {
                    "User"
                } else {
                    "Expenses"
                }, navController = navController, elevation = true, isBackNavigation = true
            )
        }, containerColor = Light100
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(
                vertical = paddingValues.calculateTopPadding(), horizontal = 16.dp
            ), horizontalAlignment = Alignment.CenterHorizontally
        ) {

        }

    }
}


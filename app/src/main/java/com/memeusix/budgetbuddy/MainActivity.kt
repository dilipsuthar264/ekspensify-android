package com.memeusix.budgetbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.memeusix.budgetbuddy.navigation.NavGraph
import com.memeusix.budgetbuddy.ui.theme.BudgetBuddyTheme
import com.memeusix.budgetbuddy.utils.SpUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT,
            )
        )
        setContent {
            val spUtils = remember { SpUtils(applicationContext) }
            val navController = rememberNavController()
            BudgetBuddyTheme() {
                NavGraph(navController, spUtils)
            }
        }
    }

}



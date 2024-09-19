package com.memeusix.budgetbuddy

import android.hardware.lights.Light
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.memeusix.budgetbuddy.navigation.NavGraph
import com.memeusix.budgetbuddy.ui.theme.BudgetBuddyTheme
import com.memeusix.budgetbuddy.ui.theme.Dark100
import com.memeusix.budgetbuddy.ui.theme.Light20
import com.memeusix.budgetbuddy.ui.theme.Violet100
import com.memeusix.budgetbuddy.ui.theme.Violet80
import com.memeusix.budgetbuddy.utils.SpUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val spUtils = remember { SpUtils(applicationContext) }
            val navController = rememberNavController()
            window?.statusBarColor = Violet100.toArgb()
            BudgetBuddyTheme() {
                NavGraph(navController, spUtils)
            }
        }
    }

}

package com.memeusix.budgetbuddy

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.compose.rememberNavController
import com.memeusix.budgetbuddy.navigation.NavGraph
import com.memeusix.budgetbuddy.ui.theme.BudgetBuddyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun getResources(): Resources {
        val baseResources = super.getResources()
        val configuration = Configuration(baseResources.configuration)
        configuration.fontScale = 1.0f
        val newContext = createConfigurationContext(configuration)
        return newContext.resources
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.auto(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT,
            )
        )
        setContent {
            val screenWidthDp = LocalConfiguration.current.screenWidthDp
            Log.e("", "onCreate: $screenWidthDp")
            val navController = rememberNavController()
            BudgetBuddyTheme() {
                Surface(modifier = Modifier.fillMaxSize()) {
                    NavGraph(navController)
                }
            }
        }
    }

}



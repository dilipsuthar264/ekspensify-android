package com.memeusix.budgetbuddy.ui.theme

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val AppColorPalette = lightColorScheme(
    primary = Violet100,
    onPrimary = Light80,
    secondary = Violet20,
    onSecondary = Violet100,
    background = Light100,
    onSurfaceVariant = Light20,
    onBackground = Dark100,
    surface = Light100,
    onSurface = Dark100,
    surfaceContainerLow = Light40,
    surfaceContainerHigh = Dark25
)

@Composable
fun BudgetBuddyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setNavigationBarColor(Light100, darkIcons = true)

//    if (darkTheme) {
//        systemUiController.setNavigationBarColor(Dark100, darkIcons = false)
//    } else {
//        systemUiController.setNavigationBarColor(Light100, darkIcons = true)
//    }
    MaterialTheme(
        colorScheme = AppColorPalette,
        typography = Typography,
        content = content,
    )
}
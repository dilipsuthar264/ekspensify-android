package com.memeusix.ekspensify.ui.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController


private val LightColorPalette = lightColorScheme(
    primary = Violet100,
    onPrimary = Light80,
    primaryContainer = Violet100,
    secondary = Violet20,
    onSecondary = Violet100,
    background = Light100,
    onBackground = Dark100,
    surface = Light100,
    onSurface = Dark100,
    onSurfaceVariant = Light20,
    surfaceContainerLow = Dark15,
    surfaceContainerHigh = Dark15,
    surfaceDim = Light80
)


private val DarkColorPalette = darkColorScheme(
    primary = Violet100_MUTED,
    onPrimary = Light100,
    primaryContainer = Violet80,
    secondary = VioletSecondary,
    onSecondary = Violet100,
    background = Dark80,
    onBackground = Light100,
    surface = Dark80,
    onSurface = Light100,
    onSurfaceVariant = Dark20,
    surfaceContainerLow = Dark50,
    surfaceContainerHigh = Dark20,
    surfaceDim = Dark90
)


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EkspensifyTheme(
    themeViewModel: ThemeViewModel = hiltViewModel(),
    content: @Composable () -> Unit
) {
    val themePreference by themeViewModel.themePreferences.collectAsState()
    val isDarkTheme = when (themePreference) {
        Theme.LIGHT.name -> false
        Theme.DARK.name -> true
        else -> isSystemInDarkTheme()
    }
    val systemUiController = rememberSystemUiController()
//    systemUiController.setSystemBarsColor(
//        color = Color.Transparent,
//        darkIcons = !isDarkTheme
//    )
    systemUiController.setStatusBarColor(
        color = Color.Transparent,
        darkIcons = !isDarkTheme
    )
    systemUiController.setNavigationBarColor(
        color = if (isDarkTheme) Color.Black else Color.White,
        darkIcons = !isDarkTheme
    )

    val colors = if (isDarkTheme) DarkColorPalette else LightColorPalette
    val extendedColors = if (isDarkTheme) DarkExtendedColors else LightExtendedColors

    CompositionLocalProvider(LocalExtendedColors provides extendedColors) {
        MaterialTheme(
            colorScheme = colors,
            typography = Typography,
            content = {
                CompositionLocalProvider(
                    LocalOverscrollConfiguration provides null,
                    content = content
                )
            }
        )
    }
}
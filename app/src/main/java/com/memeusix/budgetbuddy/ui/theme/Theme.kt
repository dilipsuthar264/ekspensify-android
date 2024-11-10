package com.memeusix.budgetbuddy.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

//
//private val DarkColorScheme = darkColorScheme(
//    primary = Violet100,
//    secondary = PurpleGrey80,
//    tertiary = Pink80
//)
//
//private val LightColorScheme = lightColorScheme(
//    primary = Purple40,
//    secondary = PurpleGrey40,
//    tertiary = Pink40
//
//    /* Other default colors to override
//    background = Color(0xFFFFFBFE),
//    surface = Color(0xFFFFFBFE),
//    onPrimary = Color.White,
//    onSecondary = Color.White,
//    onTertiary = Color.White,
//    onBackground = Color(0xFF1C1B1F),
//    onSurface = Color(0xFF1C1B1F),
//    */
//)
//
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
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AppColorPalette,
        typography = Typography, // Define Typography separately if needed
        content = content,
    )
}


//@Composable
//fun BudgetBuddyTheme(
//    darkTheme: Boolean = isSystemInDarkTheme(),
//    // Dynamic color is available on Android 12+
//    dynamicColor: Boolean = true,
//    content: @Composable () -> Unit
//) {
//    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            dynamicLightColorScheme(context)
//        }
//
//        else -> AppColorPalette
//    }
//    MaterialTheme(
//        colorScheme = colorScheme,
//        typography = Typography, // Define Typography separately if needed
//        content = content,
//    )
//}
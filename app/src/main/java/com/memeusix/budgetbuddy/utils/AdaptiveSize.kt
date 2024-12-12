package com.memeusix.budgetbuddy.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Float.toAdaptiveDp(
    smallThreshold: Int = AdaptiveDefaults.SMALL_THRESHOLD,
    mediumThreshold: Int = AdaptiveDefaults.MEDIUM_THRESHOLD,
    smallFactor: Float = AdaptiveDefaults.DP_SMALL_FACTOR,
    mediumFactor: Float = AdaptiveDefaults.DP_MEDIUM_FACTOR,
    largeFactor: Float = AdaptiveDefaults.DP_LARGE_FACTOR
): Dp {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val adjustedValue = when {
        screenWidthDp < smallThreshold -> this + smallFactor
        screenWidthDp in smallThreshold until mediumThreshold -> this + mediumFactor
        screenWidthDp >= mediumThreshold -> this + largeFactor
        else -> this
    }
    return adjustedValue.dp
}

@Composable
fun Float.toAdaptiveSp(
    smallThreshold: Int = AdaptiveDefaults.SMALL_THRESHOLD,
    mediumThreshold: Int = AdaptiveDefaults.MEDIUM_THRESHOLD,
    smallFactor: Float = AdaptiveDefaults.SP_SMALL_FACTOR,
    mediumFactor: Float = AdaptiveDefaults.SP_MEDIUM_FACTOR,
    largeFactor: Float = AdaptiveDefaults.SP_LARGE_FACTOR
): TextUnit {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    // Adjust dp size based on screen width
    val adjustedValue = when {
        screenWidthDp < smallThreshold -> this + smallFactor
        screenWidthDp in smallThreshold until mediumThreshold -> this + mediumFactor
        screenWidthDp >= mediumThreshold -> this + largeFactor
        else -> this
    }

    return adjustedValue.sp
}

@Composable
fun Float.adDp(): Dp = this.toAdaptiveDp()

@Composable
fun Int.adDp(): Dp = this.toFloat().toAdaptiveDp()

@Composable
fun Float.adSp(): TextUnit = this.toAdaptiveSp()

@Composable
fun Int.adSp(): TextUnit = this.toFloat().toAdaptiveSp()


object AdaptiveDefaults {

    // Define screen width thresholds in dp
    const val SMALL_THRESHOLD = 400   // Small screens (e.g., phones under 400dp)
    const val MEDIUM_THRESHOLD = 700  // Medium screens (e.g., phones and small tablets)
    const val LARGE_THRESHOLD = 1000  // Large screens (e.g., large tablets, landscape phones)

    // Scaling factors for `dp` based on screen size
    const val DP_SMALL_FACTOR = -8f   // Decrease dp size on small screens
    const val DP_MEDIUM_FACTOR = 0f   // Keep dp size as default on medium screens
    const val DP_LARGE_FACTOR = 8f    // Increase dp size on large screens

    // Scaling factors for `sp` based on screen size
    const val SP_SMALL_FACTOR = -2f   // Decrease text size on small screens
    const val SP_MEDIUM_FACTOR = 0f   // Keep text size as default on medium screens
    const val SP_LARGE_FACTOR = 2f    // Increase text size on large screens
}


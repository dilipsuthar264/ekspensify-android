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
    largeThreshold: Int = AdaptiveDefaults.LARGE_THRESHOLD,
    smallFactor: Float = AdaptiveDefaults.DP_SMALL_FACTOR,
    mediumFactor: Float = AdaptiveDefaults.DP_MEDIUM_FACTOR,
    largeFactor: Float = AdaptiveDefaults.DP_LARGE_FACTOR,
    xLargeFactor: Float = AdaptiveDefaults.DP_XLARGE_FACTOR
): Dp {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val adjustedValue = when {
        screenWidthDp < smallThreshold -> this + smallFactor
        screenWidthDp in smallThreshold until mediumThreshold -> this + mediumFactor
        screenWidthDp in mediumThreshold until largeThreshold -> this + largeFactor
        else -> this + xLargeFactor
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
    const val SMALL_THRESHOLD = 300
    const val MEDIUM_THRESHOLD = 500
    const val LARGE_THRESHOLD = 1000

    // Scaling factors for `dp` based on screen size
    const val DP_SMALL_FACTOR = -2f   // Decrease dp size on small screens
    const val DP_MEDIUM_FACTOR = 0f   // Keep dp size as default on medium screens
    const val DP_LARGE_FACTOR = 2f    // Increase dp size on large screens
    const val DP_XLARGE_FACTOR = 5f  // Increase dp size on extra large screens

    // Scaling factors for `sp` based on screen size
    const val SP_SMALL_FACTOR = -2f   // Decrease text size on small screens
    const val SP_MEDIUM_FACTOR = 0f   // Keep text size as default on medium screens
    const val SP_LARGE_FACTOR = 2f    // Increase text size on large screens
}


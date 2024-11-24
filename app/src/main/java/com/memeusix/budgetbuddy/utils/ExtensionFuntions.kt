package com.memeusix.budgetbuddy.utils

import android.icu.text.NumberFormat
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.navigation.NavController
import com.memeusix.budgetbuddy.data.model.responseModel.AuthResponseModel
import com.memeusix.budgetbuddy.navigation.AccountScreenRoute
import com.memeusix.budgetbuddy.navigation.BottomNavRoute
import java.util.Locale

fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPassword(): Boolean {
    return this.length >= 8
}


fun AuthResponseModel.goToNextScreenAfterLogin(
    navController: NavController
) {
    if (user?.accounts == 0) {
        navController.navigate(AccountScreenRoute()) {
            popUpTo(0) { inclusive = true }
        }
    } else {
        navController.navigate(
            BottomNavRoute
        ) {
            popUpTo(0) { inclusive = true }
        }
    }
}


fun Int.formatRupees(): String {
    val format = NumberFormat.getNumberInstance(Locale("en", "IN"))
    return format.format(this)
}

fun Modifier.drawTopAndBottomBorders(
    color: Color,
    strokeWidth: Dp
): Modifier = this.drawBehind {
    val strokePx = strokeWidth.toPx()
    val width = size.width
    drawLine(
        color = color,
        start = Offset(0f, 0f),
        end = Offset(width, 0f),
        strokeWidth = strokePx
    )
    drawLine(
        color = color,
        start = Offset(0f, size.height - strokePx / 2),
        end = Offset(width, size.height - strokePx / 2),
        strokeWidth = strokePx
    )
}


@Composable
fun Modifier.drawImageCutoutCenter(
    bitmap: ImageBitmap
): Modifier {
    return this.drawWithContent {
        with(drawContext.canvas.nativeCanvas) {
            val checkpoint = saveLayer(null, null)

            // Draw the content underneath
            drawContent()

            val centerX = (size.width / 2 - bitmap.width / 2).toInt() + 3
            val centerY = 0
            drawImage(
                image = bitmap,
                dstSize = IntSize(width = bitmap.width, height = bitmap.height),
                dstOffset = IntOffset(centerX, centerY),
                blendMode = androidx.compose.ui.graphics.BlendMode.DstOut
            )
            restoreToCount(checkpoint)
        }
    }
}

fun String.getInitials(count: Int = 2): String {
    if (this.isBlank()) return ""
    return this.split(" ")
        .filter { it.isNotEmpty() }
        .take(2)
        .joinToString("") { it[0].toString() }.uppercase()
}
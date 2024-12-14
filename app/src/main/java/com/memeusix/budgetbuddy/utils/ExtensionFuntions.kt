package com.memeusix.budgetbuddy.utils

import android.content.Context
import android.content.Intent
import android.icu.text.NumberFormat
import android.net.Uri
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
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
        .take(count)
        .joinToString("") { it[0].toString() }.uppercase()
}


fun String.generateIconSlug(): String {
    return "ic_" + this.trim().lowercase().replace(' ', '_')
}


fun Context.openImage(imageUri: Uri) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(imageUri, "image/*")
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
    }
    startActivity(intent)
}

fun Context.openImageExternally(imageUri: String?) {
    if (imageUri.orEmpty().isNotEmpty()) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(imageUri)
        }
        startActivity(intent)
    }
}


fun Modifier.dynamicPadding(paddingValues: PaddingValues): Modifier {
    return this.then(
        Modifier
            .padding(top = paddingValues.calculateTopPadding())
            .navigationBarsPadding()
            .imePadding()
    )
}


fun String.getTransactionType(): String {
    return when (this) {
        "DEBIT" -> "Expense"
        "CREDIT" -> "Income"
        else -> ""
    }
}

fun Modifier.disable(isDisable: Boolean): Modifier {
    return if (isDisable) {
        this
            .alpha(0.5f)
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        awaitPointerEvent()
                    }
                }
            }
    } else {
        this
    }
}


fun <T> MutableList<T>.toggle(item: T) {
    if (contains(item)) {
        remove(item)
    } else {
        add(item)
    }
}


inline fun <reified T : Any> NavGraphBuilder.animatedComposable(
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable<T>(
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(300)
            )
        },
        exitTransition = { ExitTransition.KeepUntilTransitionsFinished },
        popEnterTransition = { EnterTransition.None },
        content = content
    )
}

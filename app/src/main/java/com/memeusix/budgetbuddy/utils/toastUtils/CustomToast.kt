package com.memeusix.budgetbuddy.utils.toastUtils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.memeusix.budgetbuddy.ui.theme.Green120
import com.memeusix.budgetbuddy.ui.theme.Green20
import com.memeusix.budgetbuddy.ui.theme.Red120
import com.memeusix.budgetbuddy.ui.theme.Red20
import com.memeusix.budgetbuddy.ui.theme.Yellow120
import com.memeusix.budgetbuddy.ui.theme.Yellow20
import kotlinx.coroutines.delay
import kotlin.let


@Composable
fun CustomToast(toastState: CustomToastModel?, onDismiss: () -> Unit) {
    LaunchedEffect(toastState?.isVisible) {
        delay(toastState?.duration ?: 1000)
        onDismiss()
    }

    val alpha = animateFloatAsState(
        targetValue = if (toastState?.isVisible == true) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearOutSlowInEasing
        ), label = ""
    )
    toastState?.let {
        AnimatedVisibility(
            visible = it.isVisible,
            modifier = Modifier
                .imePadding()
                .alpha(alpha.value)
                .fillMaxSize(),
        ) {
            Popup(
                alignment = Alignment.BottomCenter,
                offset = IntOffset(0, -200),
                onDismissRequest = { toastState.isVisible = false }
            ) {
                Text(
                    text = toastState.message
                        ?: "",
                    style = MaterialTheme.typography.labelSmall,
                    color = when (toastState.type) {
                        ToastType.SUCCESS -> Green120
                        ToastType.INFO -> Yellow120
                        ToastType.WARNING -> Yellow120
                        ToastType.ERROR -> Red120
                    },
                    modifier = Modifier
                        .alpha(alpha.value)
                        .clip(RoundedCornerShape(20.dp))
                        .requiredWidthIn(max = LocalConfiguration.current.screenWidthDp.dp - 100.dp)
                        .background(
                            when (toastState.type) {
                                ToastType.SUCCESS -> Green20
                                ToastType.INFO -> Yellow20
                                ToastType.WARNING -> Yellow20
                                ToastType.ERROR -> Red20
                            },
                        )
                        .padding(vertical = 10.dp, horizontal = 20.dp)
                )
            }
        }
    }


}

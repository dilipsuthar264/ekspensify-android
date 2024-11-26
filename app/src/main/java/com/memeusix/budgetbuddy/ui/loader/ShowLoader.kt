package com.memeusix.budgetbuddy.ui.loader

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider

@Composable
fun ShowLoader(isLoading: Boolean) {

    if (isLoading) {
        val keyboardController = LocalSoftwareKeyboardController.current
        keyboardController?.hide()
    }

    AnimatedVisibility(
        visible = isLoading,
        enter = fadeIn(tween(200)),
        exit = fadeOut(tween(200)),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .pointerInput(Unit) {
                    detectTapGestures { }
                },
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
    }

//    if (isLoading) {
//
//        Dialog(
//            onDismissRequest = {},
//            properties = DialogProperties(
//                dismissOnBackPress = false,
//                dismissOnClickOutside = false,
//            ),
//        ) {
//            // Set dim amount
//            val window = (LocalView.current.parent as? DialogWindowProvider)?.window
//            window?.setDimAmount(0f)
//            Box(
//                modifier = Modifier
//                    .shadow(1.dp, RoundedCornerShape(16.dp))
//                    .wrapContentSize()
//                    .clip(RoundedCornerShape(16.dp))
//                    .background(MaterialTheme.colorScheme.surface)
//                    .padding(20.dp),
//                contentAlignment = Alignment.Center,
//            ) {
//                CircularProgressIndicator(
//                    color = MaterialTheme.colorScheme.primary,
//                    strokeWidth = 4.dp
//                )
//            }
//        }
//    }
}

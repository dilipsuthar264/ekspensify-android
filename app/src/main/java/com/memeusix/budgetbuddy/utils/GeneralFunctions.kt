package com.memeusix.budgetbuddy.utils

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalDensity
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val TAG: String = "GENERAL FUNCTIONS"


private var isClicked = false
fun singleClick(delayMillis: Long = 100, onClick: () -> Unit): () -> Unit {
    return {
        if (!isClicked) {
            isClicked = true
            onClick()

            // Reset `isClicked` to false after the delayMillis duration
            CoroutineScope(Dispatchers.Main).launch {
                delay(delayMillis)
                isClicked = false
            }
        }
    }
}


val gson = Gson()
inline fun <reified T> T?.toJson(): String {
    return gson.toJson(this)
}

inline fun <reified T> String?.fromJson(): T? {
    return gson.fromJson(this, object : TypeToken<T>() {}.type)
}



@Composable
fun keyboardAsState(): State<Boolean> {
    val isImeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    return rememberUpdatedState(isImeVisible)
}

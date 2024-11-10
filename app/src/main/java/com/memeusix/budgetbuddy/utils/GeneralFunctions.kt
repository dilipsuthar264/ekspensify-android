package com.memeusix.budgetbuddy.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val TAG: String = "GENERAL FUNCTIONS"


private var isClicked = false
fun singleClick(delayMillis: Long = 300, onClick: () -> Unit): () -> Unit {
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
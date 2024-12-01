package com.memeusix.budgetbuddy.ui.categories.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.memeusix.budgetbuddy.ui.theme.Violet80

@Composable
fun ShowIconLoader(modifier: Modifier) {
    CircularProgressIndicator(
        modifier = modifier
            .size(32.dp),
        color = Violet80
    )
}

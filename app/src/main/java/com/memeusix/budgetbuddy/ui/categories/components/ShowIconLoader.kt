package com.memeusix.budgetbuddy.ui.categories.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShowIconLoader(modifier: Modifier) {
    CircularProgressIndicator(
        modifier = modifier
            .size(32.dp),
        color = MaterialTheme.colorScheme.primary
    )
}

package com.memeusix.budgetbuddy.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.memeusix.budgetbuddy.ui.theme.extendedColors

@Composable
fun ListIcon(icon: Any, size: Dp = 38.dp) {
    Image(
        painter = rememberAsyncImagePainter(icon),
        contentDescription = null,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.extendedColors.imageBg)
            .size(size)
            .padding(7.dp)
    )
}
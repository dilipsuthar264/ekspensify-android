package com.memeusix.budgetbuddy.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.memeusix.budgetbuddy.ui.theme.Dark15

@Composable
fun ListIcon(@DrawableRes icon: Int) {
    Image(
        painter = rememberAsyncImagePainter(icon),
        contentDescription = null,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Dark15)
            .size(38.dp)
            .padding(7.dp)
    )
}
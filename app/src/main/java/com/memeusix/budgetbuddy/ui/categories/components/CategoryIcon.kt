package com.memeusix.budgetbuddy.ui.categories.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.memeusix.budgetbuddy.ui.theme.extendedColors

@Composable
fun CategoryIcon(image: String?) {
    AsyncImage(
        model = image,
        contentDescription = null,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.extendedColors.imageBg)
            .size(38.dp)
            .padding(7.dp)
    )
}

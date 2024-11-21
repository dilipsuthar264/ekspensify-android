package com.memeusix.budgetbuddy.ui.acounts.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CustomToggleButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier

) {
    val (textColor, background) = if (isSelected) {
        MaterialTheme.colorScheme.primary to MaterialTheme.colorScheme.secondary
    } else {
        MaterialTheme.colorScheme.onBackground to Color.Transparent
    }

    Text(
        text = text,
        color = textColor,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(
                color = background,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable(
                onClick = onClick,
                interactionSource = null,
                indication = null
            )
            .padding(vertical = 10.dp)
    )
}
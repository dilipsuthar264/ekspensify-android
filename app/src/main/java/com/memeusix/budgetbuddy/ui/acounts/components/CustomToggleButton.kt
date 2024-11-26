package com.memeusix.budgetbuddy.ui.acounts.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.memeusix.budgetbuddy.ui.theme.Dark10

@Composable
fun CustomToggleButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    radius: Dp = 10.dp,
    isBorder: Boolean = false,
    fontStyle: TextStyle = MaterialTheme.typography.bodyLarge
) {
    val (textColor, background) = if (isSelected) {
        MaterialTheme.colorScheme.primary to MaterialTheme.colorScheme.secondary
    } else {
        MaterialTheme.colorScheme.onBackground to Color.Transparent
    }

    val borderModifier = if (isBorder && !isSelected) {
        Modifier.border(1.dp, Dark10, RoundedCornerShape(radius))
    } else {
        Modifier
    }

    Text(
        text = text,
        color = textColor,
        textAlign = TextAlign.Center,
        style = fontStyle,
        modifier = Modifier
            .clip(RoundedCornerShape(radius))
            .background(
                color = background,
                shape = RoundedCornerShape(radius)
            )
            .clickable(
                onClick = onClick,
                interactionSource = null,
                indication = null
            )
            .then(borderModifier)
            .then(modifier)

    )
}


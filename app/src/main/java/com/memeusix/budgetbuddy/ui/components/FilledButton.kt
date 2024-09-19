package com.memeusix.budgetbuddy.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.memeusix.budgetbuddy.ui.theme.Typography

@Composable
fun FilledButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    textModifier: Modifier = Modifier.padding(vertical = 5.dp),
    shape: Shape = ButtonDefaults.shape
) {
    Button(
        shape = shape,
        onClick = onClick, // Use the passed onClick
        colors = colors,   // Use the passed ButtonColors
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            style = Typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            modifier = textModifier
        )
    }
}

package com.memeusix.budgetbuddy.ui.acounts.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.memeusix.budgetbuddy.utils.formatRupees

@Composable
fun AmountText(
    balance: String,
    modifier: Modifier = Modifier,
    color: Color? = null,
    size: TextUnit = 50.sp,
    fontWeight: FontWeight = FontWeight.SemiBold
) {
    val formattedBalance = balance.ifEmpty { "0" }.toInt().formatRupees()
    Text(
        text = formattedBalance,
        style = MaterialTheme.typography.titleLarge.copy(
            fontSize = size,
            fontWeight = fontWeight,
            color = color ?: Color.Unspecified
        ),
        modifier = modifier
    )
}
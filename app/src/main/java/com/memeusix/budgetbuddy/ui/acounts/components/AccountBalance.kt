package com.memeusix.budgetbuddy.ui.acounts.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memeusix.budgetbuddy.ui.theme.Dark75
import com.memeusix.budgetbuddy.ui.theme.interFontFamily

@Composable
fun AccountBalance(isListItem: Boolean, balance: String) {
    val fontWeight = if (isListItem) FontWeight.Medium else FontWeight.SemiBold
    val fontSize = if (isListItem) 16.sp else 20.sp
    Text(
        "₹$balance",
        fontFamily = interFontFamily,
        fontSize = fontSize,
        fontWeight = fontWeight,
        color = Dark75,
        modifier = Modifier.padding(horizontal = 8.dp)
    )
}

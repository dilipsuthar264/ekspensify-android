package com.memeusix.budgetbuddy.ui.dashboard.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.memeusix.budgetbuddy.ui.theme.Typography

@Composable
fun IncomeExpenseItem(
    amount: Long,
    title: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(title, style = Typography.bodySmall, color = color)
        Text("₹ $amount", style = Typography.bodyLarge, fontWeight = FontWeight.SemiBold)
    }
}


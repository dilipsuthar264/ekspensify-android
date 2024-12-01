package com.memeusix.budgetbuddy.ui.acounts.components
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.memeusix.budgetbuddy.utils.formatRupees

@Composable
fun AmountText(balance: String, modifier: Modifier = Modifier) {
    val formattedBalance = balance.ifEmpty { "0" }.toInt().formatRupees()
    Text(
        text = "₹$formattedBalance",
        style = MaterialTheme.typography.titleLarge.copy(
            fontSize = 50.sp,
            fontWeight = FontWeight.SemiBold
        ),
        modifier = modifier
    )
}
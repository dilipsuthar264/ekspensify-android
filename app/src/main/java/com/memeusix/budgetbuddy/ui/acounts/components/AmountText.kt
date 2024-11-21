package com.memeusix.budgetbuddy.ui.acounts.components


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.memeusix.budgetbuddy.utils.formatRupees


@Composable
fun AmountText(balance: String) {
    Text(
        text = buildAnnotatedString {
            append("₹${balance.ifEmpty { "0" }.toInt().formatRupees()}.")
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Normal
                )
            ) {
                append("0")
            }
        }, style = MaterialTheme.typography.titleLarge.copy(
            fontSize = 50.sp, fontWeight = FontWeight.SemiBold
        )
    )
}

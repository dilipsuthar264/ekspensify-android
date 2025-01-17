package com.memeusix.budgetbuddy.ui.dashboard.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.components.DrawableStartText
import com.memeusix.budgetbuddy.components.VerticalSpace
import com.memeusix.budgetbuddy.ui.acounts.components.AmountText
import com.memeusix.budgetbuddy.ui.theme.Dark15

@Composable
fun HorizontalAccountListItem() {
    Box(
        modifier = Modifier
            .sizeIn(minWidth = 150.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Dark15.copy(alpha = 0.6f))
            .padding(16.dp)
    ) {
        Column {
            DrawableStartText(
                text = "BOB Bank",
                icon = R.drawable.ic_bob_bank,
                colorFilter = null,
                textSize = 14.sp,
                fontWeight = FontWeight.Medium,
                iconSpace = 8.dp,
                iconSize = 16.dp
            )
            VerticalSpace(10.dp)
            Text(
                "Available Balance",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 10.sp
                )
            )
            VerticalSpace(2.dp)
            AmountText("135133", size = 20.sp)
        }
    }
}


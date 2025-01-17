package com.memeusix.budgetbuddy.ui.dashboard.home.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.components.DrawableEndText
import com.memeusix.budgetbuddy.components.VerticalSpace
import com.memeusix.budgetbuddy.ui.theme.extendedColors
import com.memeusix.budgetbuddy.utils.roundedBorder


@Composable
fun HomeAppBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column() {
            Text(
                "Good Morning", style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
            VerticalSpace(2.dp)
            Text(
                "Dilip", style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                )
            )
        }
        DrawableEndText(
            text = "This Week",
            icon = R.drawable.ic_arrow_down,
            iconSize = 22.dp,
            textSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.extendedColors.iconColor,
            modifier = Modifier
                .fillMaxHeight()
                .roundedBorder(40.dp)
                .padding(start = 16.dp, end = 8.dp)
        )
    }
}


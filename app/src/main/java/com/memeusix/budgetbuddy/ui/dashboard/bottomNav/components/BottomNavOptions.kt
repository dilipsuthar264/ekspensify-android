package com.memeusix.budgetbuddy.ui.dashboard.bottomNav.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memeusix.budgetbuddy.ui.dashboard.bottomNav.BottomNavItem
import com.memeusix.budgetbuddy.ui.theme.Typography

@Composable
fun BottomNavOptions(
    item: BottomNavItem,
    isSelected: Boolean,
    modifier: Modifier,
    onClick: () -> Unit
) {
    val icon = remember(isSelected) {
        if (isSelected) item.selectedIcon else item.icon
    }
    Column(
        modifier = modifier
            .padding(vertical = 14.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)
    ) {
        Image(
            painterResource(icon),
            contentDescription = item.label,
            modifier = Modifier
                .size(24.dp)
        )
        Text(
            item.label,
            style = Typography.bodyLarge.copy(fontSize = 10.sp),
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
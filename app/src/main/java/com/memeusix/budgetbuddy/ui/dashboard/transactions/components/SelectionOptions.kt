package com.memeusix.budgetbuddy.ui.dashboard.transactions.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.components.CustomListItem
import com.memeusix.budgetbuddy.components.ListIcon
import com.memeusix.budgetbuddy.data.model.responseModel.AccountResponseModel
import com.memeusix.budgetbuddy.data.model.responseModel.CategoryResponseModel
import com.memeusix.budgetbuddy.ui.acounts.data.BankModel
import com.memeusix.budgetbuddy.ui.theme.Dark10
import com.memeusix.budgetbuddy.ui.theme.Light20


@Composable
fun SelectionOptions(
    disable: Boolean = false,
    isCategory: Boolean,
    selectedCategory: CategoryResponseModel?,
    selectedAccount: AccountResponseModel?,
    onClick: () -> Unit
) {
    val iconAndTitle = remember(selectedCategory, selectedAccount) {
        if (isCategory) {
            selectedCategory?.let { category ->
                category.icon to category.name
            }
        } else {
            selectedAccount?.let { account ->
                BankModel.getAccounts()
                    .find { it.iconSlug == account.icon }?.icon to account.name
            }
        }
    }

    val (icon, title) = iconAndTitle ?: (null to null)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(56.dp)
            .border(1.dp, Dark10, RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        val titleStyle = if (title == null) {
            MaterialTheme.typography.bodyLarge.copy(
                fontSize = 16.sp, fontWeight = FontWeight.Normal, color = Light20
            )
        } else {
            MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        CustomListItem(
            leadingContent = {
                if (icon != null) {
                    ListIcon(icon)
                }
            },
            enable = !disable,
            title = title ?: if (isCategory) "Selected Category" else "Selected Account",
            titleStyle = titleStyle,
            trailingContent = {
                Icon(
                    rememberAsyncImagePainter(R.drawable.ic_arrow_down),
                    contentDescription = null,
                    tint = Light20,
                    modifier = Modifier.size(30.dp)
                )
            },
            onClick = onClick
        )
    }
}
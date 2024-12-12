package com.memeusix.budgetbuddy.ui.dashboard.transactions.filterComponets


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.components.CustomListItem
import com.memeusix.budgetbuddy.data.model.responseModel.AccountResponseModel
import com.memeusix.budgetbuddy.data.model.responseModel.CategoryResponseModel
import com.memeusix.budgetbuddy.ui.acounts.data.BankModel
import com.memeusix.budgetbuddy.ui.dashboard.transactions.data.AmountRange
import com.memeusix.budgetbuddy.ui.dashboard.transactions.data.DateRange
import com.memeusix.budgetbuddy.ui.dashboard.transactions.data.SortBy
import com.memeusix.budgetbuddy.ui.theme.Light40
import com.memeusix.budgetbuddy.utils.TransactionType
import java.io.Serializable

@Composable
fun FilterValuesList(item: Serializable, isSelected: Boolean, onClick: () -> Unit) {

    val itemIcon = remember(item) {
        when (item) {
            is CategoryResponseModel -> item.icon
            is AccountResponseModel -> BankModel.getAccounts()
                .find { it.iconSlug == item.icon }?.icon

            else -> null
        }
    }
    CustomListItem(
        title = when (item) {
            is CategoryResponseModel -> item.name.orEmpty()
            is AccountResponseModel -> item.name.orEmpty()
            is DateRange -> item.displayText
            is AmountRange -> item.displayText
            is SortBy -> item.displayText
            is TransactionType -> item.displayName
            else -> item.toString()
        },
        titleStyle = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.Normal,
        ),
        leadingContent = {
            itemIcon?.let {
                FilterListIcon(itemIcon)
            }
        },
        trailingContent = {
            if (isSelected) {
                FilterListIcon(R.drawable.ic_success)
            }
        },
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .let { if (isSelected) it.background(Light40) else it }
            .clickable(
                interactionSource = null,
                indication = null,
                onClick = onClick
            )
            .padding(12.dp),
        enable = false
    )
}


@Composable
private fun FilterListIcon(item: Any?) {
    AsyncImage(
        model = item,
        contentDescription = null,
        modifier = Modifier
            .size(18.dp)
    )
}


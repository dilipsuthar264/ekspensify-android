package com.memeusix.budgetbuddy.ui.dashboard.transactions.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.data.model.responseModel.AccountResponseModel
import com.memeusix.budgetbuddy.data.model.responseModel.CategoryResponseModel
import com.memeusix.budgetbuddy.ui.acounts.components.AccountListItem
import com.memeusix.budgetbuddy.ui.theme.Light60
import com.memeusix.budgetbuddy.utils.BottomSheetType

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CategoryAndAccountBottomSheet(
    selectedCategory: MutableState<CategoryResponseModel>,
    selectedAccount: MutableState<AccountResponseModel>,
    categories: List<CategoryResponseModel>,
    accounts: List<AccountResponseModel>,
    type: BottomSheetType,
    onDismiss: () -> Unit
) {
    val state = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    ModalBottomSheet(
        sheetState = state,
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
    ) {
        Column(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                "Select ${type.getVal()}",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )
            when (type) {
                BottomSheetType.CATEGORY -> {
                    CategoryList(categories, selectedCategory, onDismiss)
                }

                BottomSheetType.ACCOUNT -> {
                    AccountList(accounts, selectedAccount, onDismiss)
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun CategoryList(
    categories: List<CategoryResponseModel>,
    selectedCategory: MutableState<CategoryResponseModel>,
    onDismiss: () -> Unit
) {
    FlowRow(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        categories.forEach { category ->
            val isSelected = selectedCategory.value.id == category.id
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Light60)
                    .clickable {
                        selectedCategory.value = category
                        onDismiss()
                    }
                    .border(
                        1.dp,
                        if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                        RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                AsyncImage(
                    category.icon,
                    placeholder = painterResource(R.drawable.ic_transaction),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    category.name.orEmpty(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
private fun AccountList(
    accounts: List<AccountResponseModel>,
    selectedAccount: MutableState<AccountResponseModel>,
    onDismiss: () -> Unit
) {
    LazyColumn() {
        items(accounts, key = { it.id!! }) { account ->
            val isSelected = selectedAccount.value.id == account.id
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .border(
                        1.dp,
                        if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                        RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 6.dp)
            ) {
                AccountListItem(
                    isListItem = true,
                    account = account,
                    onClick = {
                        selectedAccount.value = account
                        onDismiss()
                    }
                )
            }
        }
    }
}
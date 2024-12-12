package com.memeusix.budgetbuddy.ui.dashboard.transactions.filterComponets


import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.data.model.responseModel.AccountResponseModel
import com.memeusix.budgetbuddy.ui.acounts.data.BankModel
import com.memeusix.budgetbuddy.ui.dashboard.transactions.viewmodel.TransactionViewModel
import com.memeusix.budgetbuddy.ui.theme.Dark10
import com.memeusix.budgetbuddy.ui.theme.Light20
import com.memeusix.budgetbuddy.ui.theme.Violet100
import com.memeusix.budgetbuddy.utils.AccountType

@Composable
fun SearchBar(
    searchState: MutableState<String>,
    onSearch: KeyboardActionScope.() -> Unit,
    onFilterClick: () -> Unit,
    isFilterApplied: Boolean = false
) {
    Row(
        Modifier
            .clip(RoundedCornerShape(12.dp))
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .height(50.dp)
            .fillMaxWidth()
            .border(1.dp, Dark10, RoundedCornerShape(12.dp))
            .animateContentSize()
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(R.drawable.ic_search),
            contentDescription = null,
            Modifier
                .size(30.dp)
        )
        BasicTextField(
            value = searchState.value,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 10.dp),
            onValueChange = { if (it.length <= 20) searchState.value = it },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = onSearch),
            textStyle = MaterialTheme.typography.bodyLarge,
            decorationBox = { innerBox ->
                if (searchState.value.isEmpty()) Text(
                    "Search..",
                    color = Light20,
                    style = MaterialTheme.typography.bodyLarge
                )
                innerBox()
            }
        )
        val painter = rememberAsyncImagePainter(
            if (isFilterApplied) R.drawable.ic_selected_filter else R.drawable.ic_filter
        )
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(30.dp)
                .clickable(onClick = onFilterClick)
        )
    }
}


@Composable
private fun HorizontalAccountListItem(
    account: AccountResponseModel, viewModel: TransactionViewModel
) {
    val filterState = viewModel.filterState.collectAsState()
    val iconResource by remember(account.type, account.icon) {
        derivedStateOf {
            if (account.type == AccountType.BANK.toString()) {
                BankModel.getBanks().find { it.iconSlug == account.icon }?.icon
            } else {
                BankModel.getWallet().find { it.iconSlug == account.icon }?.icon
            }
        }
    }
    val isSelected by remember(filterState.value.accountId) { derivedStateOf { filterState.value.accountId == account.id } }

    HorizontalFilterIconWithIndicator(
        iconResource,
        isSelected,
        onClick = {
            viewModel.updateFilter(
                viewModel.filterState.value.copy(
                    accountId = account.id
                )
            )
        })
}

@Composable
private fun HorizontalFilterIconWithIndicator(
    iconResource: Int?,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val indicationColor by rememberUpdatedState(if (isSelected) Violet100 else Dark10)

    Column(
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = null
            ),
        verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(iconResource),
            contentDescription = null,
            modifier = Modifier
                .size(30.dp)
                .padding(2.dp)
        )
        Box(
            modifier = Modifier
                .background(indicationColor, CircleShape)
                .height(3.dp)
                .width(12.dp)
        )
    }
}
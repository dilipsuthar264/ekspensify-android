package com.memeusix.budgetbuddy.ui.acounts.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastSumBy
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.components.CustomListItem
import com.memeusix.budgetbuddy.data.model.responseModel.AccountResponseModel
import com.memeusix.budgetbuddy.utils.AccountType
import com.memeusix.budgetbuddy.utils.formatRupees

@Composable
fun AccountCardFooter(
    accountList: List<AccountResponseModel>,
    selectedAccountType: AccountType,
) {
    val filteredList = remember(accountList, selectedAccountType) {
        accountList.filter { it.type == selectedAccountType.toString() }
    }
    val totalBalance = remember(filteredList) {
        filteredList.fastSumBy { it.balance ?: 0 }
    }
    CustomListItem(
        title = stringResource(R.string.total),
        subtitle = "${filteredList.size} ${selectedAccountType.getDisplayName()} Accounts",
        modifier = Modifier.padding(10.dp),
        trailingContent = {
            AccountBalance(false, totalBalance.formatRupees())
        },
        enable = false,
        onClick = {}
    )
}
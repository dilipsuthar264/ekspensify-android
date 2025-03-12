package com.ekspensify.app.ui.acounts.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastSumBy
import com.ekspensify.app.R
import com.ekspensify.app.components.CustomListItem
import com.ekspensify.app.data.model.responseModel.AccountResponseModel
import com.ekspensify.app.utils.AccountType
import com.ekspensify.app.utils.formatRupees

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
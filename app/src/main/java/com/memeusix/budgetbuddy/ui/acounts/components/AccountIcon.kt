package com.memeusix.budgetbuddy.ui.acounts.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.memeusix.budgetbuddy.components.ListIcon
import com.memeusix.budgetbuddy.data.model.responseModel.AccountResponseModel
import com.memeusix.budgetbuddy.ui.acounts.data.BankModel
import com.memeusix.budgetbuddy.utils.AccountType

@Composable
fun AccountIcon(account: AccountResponseModel, size: Dp = 36.dp) {
    val iconResource = if (account.type == AccountType.BANK.toString()) {
        BankModel.getBanks().find { it.iconSlug == account.icon }?.icon
    } else {
        BankModel.getWallet().find { it.iconSlug == account.icon }?.icon
    }
    iconResource?.let { icon ->
        ListIcon(icon, size)
    }
}

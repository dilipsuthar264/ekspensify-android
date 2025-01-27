package com.memeusix.ekspensify.ui.acounts.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.memeusix.ekspensify.components.ListIcon
import com.memeusix.ekspensify.data.model.responseModel.AccountResponseModel
import com.memeusix.ekspensify.ui.acounts.data.BankModel
import com.memeusix.ekspensify.utils.AccountType

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

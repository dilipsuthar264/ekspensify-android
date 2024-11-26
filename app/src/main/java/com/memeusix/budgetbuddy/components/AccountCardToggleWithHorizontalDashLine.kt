package com.memeusix.budgetbuddy.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.memeusix.budgetbuddy.ui.acounts.components.CustomToggleButton
import com.memeusix.budgetbuddy.utils.AccountType

@Composable
fun AccountCardToggleWithHorizontalDashLine(
    selectedAccountType: AccountType,
    modifier: Modifier = Modifier,
    onTypeChange: (AccountType) -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CustomToggleButton(
            text = AccountType.BANK.getVal(),
            isSelected = selectedAccountType == AccountType.BANK,
            modifier = Modifier.weight(1f).padding(vertical = 10.dp),
            onClick = {
                onTypeChange(AccountType.BANK)
            }
        )
        CustomToggleButton(
            text = AccountType.WALLET.getVal(),
            isSelected = selectedAccountType == AccountType.WALLET,
            modifier = Modifier.weight(1f).padding(vertical = 10.dp),
            onClick = {
                onTypeChange(AccountType.WALLET)
            }
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
    HorizontalDashedLine()
}
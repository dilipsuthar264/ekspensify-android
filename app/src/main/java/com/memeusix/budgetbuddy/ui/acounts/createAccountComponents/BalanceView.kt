package com.memeusix.budgetbuddy.ui.acounts.createAccountComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.data.model.TextFieldStateModel
import com.memeusix.budgetbuddy.ui.acounts.components.AmountText
import com.memeusix.budgetbuddy.ui.acounts.data.BankModel

@Composable
fun BalanceView(
    balanceState: MutableState<TextFieldStateModel>,
    selectedAccount: BankModel?,
    showDelete: Boolean,
    onDeleteClick: () -> Unit
) {
    Text(
        text = "Balance", style = MaterialTheme.typography.titleSmall
    )
    AmountText(balanceState.value.text)
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .height(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        selectedAccount?.icon?.let {
            Image(
                painterResource(it), contentDescription = null, modifier = Modifier.fillMaxHeight()
            )
        }
        Text(
            selectedAccount?.name ?: "No Account Selected",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold,
            ),
        )
        Spacer(modifier = Modifier.weight(1f))
        if (showDelete) {
            Image(painterResource(R.drawable.ic_delete),
                contentDescription = null,
                modifier = Modifier.clickable { onDeleteClick() })
        }
    }
}
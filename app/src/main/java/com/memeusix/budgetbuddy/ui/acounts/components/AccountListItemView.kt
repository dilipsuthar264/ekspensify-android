package com.memeusix.budgetbuddy.ui.acounts.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memeusix.budgetbuddy.data.model.responseModel.AccountResponseModel
import com.memeusix.budgetbuddy.ui.acounts.model.BankModel
import com.memeusix.budgetbuddy.ui.theme.Dark15
import com.memeusix.budgetbuddy.ui.theme.Dark75
import com.memeusix.budgetbuddy.ui.theme.interFontFamily
import com.memeusix.budgetbuddy.utils.AccountType
import com.memeusix.budgetbuddy.utils.formatRupees

@Composable
fun AccountListItem(
    isListItem: Boolean = true,
    account: AccountResponseModel,
    listCountString: String = "",
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = isListItem,
                onClick = onClick
            )
            .padding(10.dp)
    ) {
        if (isListItem) {
            AccountIcon(account)
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                account.name ?: "",
                fontFamily = interFontFamily,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground,
            )
            if (!isListItem) {
                Text(
                    listCountString,
                    fontFamily = interFontFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }

        AccountBalance(isListItem, account.balance?.formatRupees() ?: "0")
    }
}

@Composable
private fun AccountBalance(isListItem: Boolean, balance: String) {
    val fontWeight = if (isListItem) FontWeight.Medium else FontWeight.SemiBold
    val fontSize = if (isListItem) 16.sp else 20.sp
    Text(
        "₹$balance",
        fontFamily = interFontFamily,
        fontSize = fontSize,
        fontWeight = fontWeight,
        color = Dark75,
        modifier = Modifier.padding(horizontal = 8.dp)
    )
}

@Composable
private fun AccountIcon(account: AccountResponseModel) {
    val iconResource = if (account.type == AccountType.BANK.toString()) {
        BankModel.getBanks().find { it.iconSlug == account.icon }?.icon
    } else {
        BankModel.getWallet().find { it.iconSlug == account.icon }?.icon
    }
    iconResource?.let { icon ->
        Image(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Dark15)
                .size(36.dp)
                .padding(7.dp)
        )
    }
}
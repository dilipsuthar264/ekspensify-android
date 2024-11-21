package com.memeusix.budgetbuddy.ui.acounts.createAccountComponents

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.memeusix.budgetbuddy.ui.acounts.components.AccountTypeGridItem
import com.memeusix.budgetbuddy.ui.acounts.model.BankModel
import com.memeusix.budgetbuddy.ui.components.AccountCardToggleWithHorizontalDashLine
import com.memeusix.budgetbuddy.ui.theme.Light20
import com.memeusix.budgetbuddy.utils.AccountType

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AccountTypeCard(
    selectedAccountType: AccountType,
    walletList: List<BankModel>,
    bankList: List<BankModel>,
    selectedWallet: BankModel?,
    selectedBank: BankModel?,
    onTypeChange: (AccountType) -> Unit,
    onSelectItem: (BankModel) -> Unit,
) {
    val displayedItems by remember(selectedAccountType, walletList, bankList) {
        derivedStateOf {
            when (selectedAccountType) {
                AccountType.WALLET -> walletList
                AccountType.BANK -> bankList
            }
        }
    }
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .border(
                width = 1.dp, color = Light20, RoundedCornerShape(16.dp)
            )
            .padding(15.dp)
            .animateContentSize(),
    ) {
        AccountCardToggleWithHorizontalDashLine(
            selectedAccountType = selectedAccountType, onTypeChange = onTypeChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.spacedBy(7.dp),
//            overflow = FlowRowOverflow.expandIndicator {
//                AccountTypeGridItem(
//                    BankModel(name = "More", icon = null, iconSlug = ""),
//                    modifier = Modifier.width(70.dp),
//                    onClick = { },
//                    isText = true
//                )
//            },
        ) {

            val itemsPerRow = 4
            val totalItems = displayedItems.size
            val remainder = totalItems % itemsPerRow

            displayedItems.forEach { item ->
                val accountItem =
                    item.copy(isSelected = item.iconSlug == selectedBank?.iconSlug || item.iconSlug == selectedWallet?.iconSlug)
                AccountTypeGridItem(
                    accountItem,
                    modifier = Modifier.width(70.dp),
                    onClick = { onSelectItem(item) },
                )
            }
            // Add placeholders to align the last row
            if (remainder != 0) {
                val placeholders = itemsPerRow - remainder
                repeat(placeholders) {
                    Spacer(modifier = Modifier.width(70.dp))
                }
            }
        }

    }
}

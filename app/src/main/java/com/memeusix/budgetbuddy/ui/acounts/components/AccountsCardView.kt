package com.memeusix.budgetbuddy.ui.acounts.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.memeusix.budgetbuddy.components.HorizontalDashedLine
import com.memeusix.budgetbuddy.ui.theme.Dark10
import com.memeusix.budgetbuddy.utils.AccountType

@Composable
fun AccountsCardView(
    selectedAccountType: AccountType,
    onTypeChange: (AccountType) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .border(1.dp, Dark10, RoundedCornerShape(16.dp))
            .animateContentSize()
    ) {
        AccountTypeTabButton(
            selectedAccountType = selectedAccountType,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            onTypeChange = onTypeChange
        )
        HorizontalDashedLine()
        // Loading Content
        content()
    }
}
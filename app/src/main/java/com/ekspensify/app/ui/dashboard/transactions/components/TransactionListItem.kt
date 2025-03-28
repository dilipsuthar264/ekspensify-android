package com.ekspensify.app.ui.dashboard.transactions.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ekspensify.app.components.CustomListItem
import com.ekspensify.app.data.model.responseModel.TransactionResponseModel
import com.ekspensify.app.ui.categories.components.CategoryIcon
import com.ekspensify.app.ui.theme.Green100
import com.ekspensify.app.ui.theme.Red100
import com.ekspensify.app.utils.DateFormat
import com.ekspensify.app.utils.TransactionType
import com.ekspensify.app.utils.formatDateTime
import com.ekspensify.app.utils.formatRupees
import com.ekspensify.app.utils.getColor

@Composable
fun TransactionListItem(
    transaction: TransactionResponseModel?,
    onClick: (TransactionResponseModel?) -> Unit,
    isEnable: Boolean,
    modifier: Modifier
) {
    CustomListItem(
        modifier = modifier,
        title = transaction?.category?.name.orEmpty(),
        subtitle = transaction?.account?.name.orEmpty(),
        desc = transaction?.note.orEmpty(),
        leadingContent = {
            CategoryIcon(transaction?.category?.icon, getColor(transaction?.category?.icFillColor).copy(alpha = 0.2f))
        },
        trailingContent = {
            TransactionTrailingContent(transaction)
        },
        onClick = {
            onClick(transaction)
        },
        enable = isEnable
    )
}


@Composable
fun TransactionTrailingContent(transaction: TransactionResponseModel?) {
    val amount = remember(transaction?.type, transaction?.amount) {
        if (transaction?.type == TransactionType.CREDIT.toString()) {
            "+${transaction.amount?.formatRupees()}"
        } else {
            "-${transaction?.amount?.formatRupees()}"
        }
    }
    val dataTime =
        remember(transaction?.createdAt) { transaction?.createdAt.formatDateTime(DateFormat.MMM_dd_yyyy_hh_mm_a) }

    val color = remember(transaction?.type) {
        if (transaction?.type == TransactionType.CREDIT.toString()) Green100 else Red100
    }

    Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text(
            amount, style = MaterialTheme.typography.bodyLarge.copy(color = color)
        )
        Text(
            dataTime, style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.sp,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}
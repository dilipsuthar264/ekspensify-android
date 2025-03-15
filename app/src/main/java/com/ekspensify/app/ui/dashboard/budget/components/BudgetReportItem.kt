package com.ekspensify.app.ui.dashboard.budget.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ekspensify.app.R
import com.ekspensify.app.components.CustomListItem
import com.ekspensify.app.components.DrawableEndText
import com.ekspensify.app.data.model.responseModel.BudgetReportResponseModel
import com.ekspensify.app.data.model.responseModel.BudgetResponseModel
import com.ekspensify.app.ui.dashboard.budget.helper.getReportPeriod
import com.ekspensify.app.ui.dashboard.budget.helper.getStartEndDateText
import com.ekspensify.app.ui.dashboard.budget.helper.isBudgetExceed
import com.ekspensify.app.ui.theme.Green100
import com.ekspensify.app.ui.theme.Red100
import com.ekspensify.app.utils.formatRupees

@Composable
fun BudgetReportItem(
    report: BudgetReportResponseModel?,
    budgetDetails: BudgetResponseModel?,
    onClick: () -> Unit
) {
    val dateText =
        getStartEndDateText(report?.periodStartDate, report?.periodEndDate)
    val color = if (isBudgetExceed(
            budgetDetails?.limit,
            report?.amount
        )
    ) Red100 else Green100

    CustomListItem(
        title = report.getReportPeriod(budgetDetails?.period ?: ""),
        subtitle = dateText,
        modifier = Modifier.padding(
            vertical = 15.dp,
            horizontal = 20.dp
        ),
        onClick = onClick,
        trailingContent = {
            BudgetReportTrailing(report, color)
        }
    )
}

@Composable
private fun BudgetReportTrailing(
    report: BudgetReportResponseModel?,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            (report?.amount ?: 0.0).formatRupees(),
            style = MaterialTheme.typography.bodyLarge.copy(color = color)
        )
        DrawableEndText(
            text = "See ${report?.totalTransactions} Transactions",
            textSize = 10.sp,
            icon = R.drawable.ic_arrow_right,
            iconSize = 16.dp,
        )
    }
}

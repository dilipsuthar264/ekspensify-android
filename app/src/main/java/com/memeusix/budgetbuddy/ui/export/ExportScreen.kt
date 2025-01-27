package com.memeusix.budgetbuddy.ui.export

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.components.AppBar
import com.memeusix.budgetbuddy.components.DrawableEndText
import com.memeusix.budgetbuddy.components.FilledButton
import com.memeusix.budgetbuddy.components.MonthPicker
import com.memeusix.budgetbuddy.components.ShowLoader
import com.memeusix.budgetbuddy.components.VerticalSpace
import com.memeusix.budgetbuddy.data.ApiResponse
import com.memeusix.budgetbuddy.data.model.requestModel.ExportRequestModel
import com.memeusix.budgetbuddy.ui.dashboard.budget.components.CreateBudgetSectionCard
import com.memeusix.budgetbuddy.ui.dashboard.budget.components.DateFieldWithIcon
import com.memeusix.budgetbuddy.ui.export.componants.SelectFileFormatBtns
import com.memeusix.budgetbuddy.ui.export.viewModel.ExportViewModel
import com.memeusix.budgetbuddy.utils.ExportFileFormat
import com.memeusix.budgetbuddy.utils.dynamicImePadding
import com.memeusix.budgetbuddy.utils.firstLetterCap
import com.memeusix.budgetbuddy.utils.handleApiResponse
import com.memeusix.budgetbuddy.utils.toastUtils.CustomToast
import com.memeusix.budgetbuddy.utils.toastUtils.CustomToastModel
import java.time.LocalDate

@Composable
fun ExportScreen(
    navController: NavHostController, exportViewModel: ExportViewModel = hiltViewModel()
) {
    val selectedFormat = remember { mutableStateOf(ExportFileFormat.PDF) }
    var showMonthPicker by remember { mutableStateOf(false) }
    var selectedStartDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedEndDate by remember { mutableStateOf<LocalDate?>(null) }

    // custom Toast
    val toastState = remember { mutableStateOf<CustomToastModel?>(null) }
    CustomToast(toastState)

    // Date Picker
    if (showMonthPicker) {
        MonthPicker(
            initialMonth = selectedStartDate ?: LocalDate.now(),
            onDismiss = { showMonthPicker = false },
            onDateSelected = { (startDate, endDate) ->
                selectedStartDate = startDate
                selectedEndDate = endDate
                showMonthPicker = false
            },
        )
    }

    // SuccessDialog
    var showSuccessDialog by remember { mutableStateOf(false) }
    if (showSuccessDialog) {
        ExportSuccessDialog(
            date = selectedStartDate,
            email = exportViewModel.user?.email,
            onDismiss = { showSuccessDialog = false }
        )
    }

    // api State
    val requestStatementsState by exportViewModel.requestStatements.collectAsStateWithLifecycle()

    val isLoading = requestStatementsState is ApiResponse.Loading

    LaunchedEffect(requestStatementsState) {
        handleApiResponse(response = requestStatementsState,
            toastState = toastState,
            navController = navController,
            onSuccess = { data ->
                showSuccessDialog = true
            })
    }




    Scaffold(topBar = {
        AppBar(
            heading = stringResource(R.string.export), navController = navController
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .dynamicImePadding(paddingValues)
                .padding(horizontal = 20.dp)
        ) {

            SelectionSection(selectedFormat = selectedFormat,
                selectedMonth = if (selectedStartDate != null) "${
                    selectedStartDate?.month?.name?.firstLetterCap()
                }, ${selectedStartDate?.year}" else null,
                onDatePickerClick = {
                    showMonthPicker = true
                })
            VerticalSpace(16.dp)
            DetailsSections(exportViewModel.user?.email ?: "")
            Spacer(Modifier.weight(1f))
            VerticalSpace(16.dp)
            FilledButton(text = stringResource(R.string.send_request),
                textModifier = Modifier.padding(vertical = 17.dp),
                modifier = Modifier.padding(bottom = 20.dp),
                enabled = selectedStartDate != null,
                onClick = {
                    val exportRequestModel = ExportRequestModel(
                        start = selectedStartDate?.atStartOfDay().toString(),
                        end = selectedEndDate?.plusDays(1)?.atStartOfDay().toString(),
                        format = selectedFormat.value.name
                    )
                    exportViewModel.requestStatements(exportRequestModel)
                })
        }

        // Show Loader
        ShowLoader(isLoading)
    }
}

@Composable
private fun SelectionSection(
    selectedFormat: MutableState<ExportFileFormat>,
    onDatePickerClick: () -> Unit,
    selectedMonth: String? = null
) {
    CreateBudgetSectionCard(title = stringResource(R.string.select_month)) {
        DateFieldWithIcon(
            text = selectedMonth ?: "Select Month",
            isSelected = !selectedMonth.isNullOrEmpty(),
            onClick = onDatePickerClick
        )
        Text(
            stringResource(R.string.file_format), style = MaterialTheme.typography.bodyMedium
        )
        SelectFileFormatBtns(selectedFormat)
    }
}


@Composable
private fun DetailsSections(email: String) {
    CreateBudgetSectionCard(title = "") {
        Text(
            stringResource(R.string.export_transaction_statement),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium
            )
        )
        Text(
            stringResource(R.string.your_transaction_statement_will_be_sent_to_your_verified_email_address),
            style = MaterialTheme.typography.labelMedium
        )
        DrawableEndText(
            text = email,
            icon = R.drawable.ic_verified_check,
            fontWeight = FontWeight.Bold,
            textSize = 12.sp,
            iconSize = 14.dp,
            colorFilter = false,
        )
        Text(
            text = stringResource(R.string.for_security_reasons_the_statement_can_only_be_sent_to_this_email),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

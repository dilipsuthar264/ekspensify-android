package com.ekspensify.app.ui.dashboard.budget

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.ekspensify.app.R
import com.ekspensify.app.components.AppBar
import com.ekspensify.app.components.EmptyView
import com.ekspensify.app.components.PullToRefreshLayout
import com.ekspensify.app.navigation.BottomNavRoute
import com.ekspensify.app.navigation.BudgetTransactionScreenRoute
import com.ekspensify.app.ui.dashboard.budget.viewModel.BudgetViewModel
import com.ekspensify.app.ui.dashboard.transactions.TransactionDetailsDialog
import com.ekspensify.app.ui.dashboard.transactions.TransactionListContent
import com.ekspensify.app.ui.dashboard.transactions.viewmodel.TransactionViewModel
import com.ekspensify.app.utils.NavigationRequestKeys
import com.ekspensify.app.utils.dynamicImePadding
import com.ekspensify.app.utils.getViewModelStoreOwner
import com.ekspensify.app.utils.handleApiResponse
import com.ekspensify.app.utils.toastUtils.CustomToast
import com.ekspensify.app.utils.toastUtils.CustomToastModel

@Composable
fun BudgetTransactionScreen(
    navController: NavHostController,
    args: BudgetTransactionScreenRoute,
    budgetViewModel: BudgetViewModel = hiltViewModel(navController.getViewModelStoreOwner()),
    transactionViewModel: TransactionViewModel = hiltViewModel(navController.getViewModelStoreOwner())
) {
    LaunchedEffect(Unit) {
        budgetViewModel.setBudgetId(null)
        budgetViewModel.setReportId(null)
//        delay(100)
        budgetViewModel.setBudgetId(args.budgetId)
        budgetViewModel.setReportId(args.budgetReportId)
    }

    val toastState = remember { mutableStateOf<CustomToastModel?>(null) }
    CustomToast(toastState)

    val transactions = budgetViewModel.getBudgetTransaction().collectAsLazyPagingItems()
    val dialogState = transactionViewModel.transactionDetails.collectAsStateWithLifecycle()
    val deleteTransaction by transactionViewModel.deleteTransaction.collectAsStateWithLifecycle()

    val showDialog = rememberUpdatedState(dialogState.value != null)

    val saveState = navController.currentBackStackEntry?.savedStateHandle
    LaunchedEffect(deleteTransaction) {
        handleApiResponse(
            response = deleteTransaction,
            toastState = toastState,
            navController = navController,
            onSuccess = {
                navController.previousBackStackEntry?.savedStateHandle?.set(
                    NavigationRequestKeys.REFRESH_TRANSACTION, true
                )
                navController.popBackStack(BottomNavRoute, false)
            })

        saveState?.getLiveData<Boolean>(NavigationRequestKeys.REFRESH_TRANSACTION)
            ?.observeForever { result ->
                if (result == true) {
                    budgetViewModel.refreshBudgets()
                    transactions.refresh()
                    transactionViewModel.closeDialog()
                    saveState.remove<Boolean>(NavigationRequestKeys.REFRESH_TRANSACTION)
                }
            }

    }
    if (showDialog.value) {
        TransactionDetailsDialog(transaction = dialogState.value,
            navController = navController,
            onDeleteClick = { id ->
                id?.let {
                    transactionViewModel.deleteTransaction(it)
                    transactionViewModel.closeDialog()
                }
            },
            onDismiss = {
                transactionViewModel.closeDialog()
            })
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
        AppBar(
            heading = stringResource(R.string.budget_transactions, args.budgetId),
            navController = navController,
        )
    }) { paddingValues ->
        PullToRefreshLayout(
            onRefresh = transactions::refresh,
            modifier = Modifier
                .dynamicImePadding(paddingValues)
                .fillMaxSize()
        ) {
            if (transactions.itemCount == 0 && transactions.loadState.isIdle) {
                EmptyView(
                    title = stringResource(R.string.no_transactions_found),
                    description = stringResource(R.string.there_s_nothing_here_at_the_moment_create_a_transaction_to_continue_tracking)
                )
            }
            TransactionListContent(
                modifier = Modifier.fillMaxSize(),
                transactions = transactions,
                isEnable = true,
                onClick = { transaction ->
                    transactionViewModel.openDialog(transaction)
                })
        }
    }

}
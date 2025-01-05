package com.memeusix.budgetbuddy.ui.dashboard.budget

import android.util.Log
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
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.components.AppBar
import com.memeusix.budgetbuddy.components.EmptyView
import com.memeusix.budgetbuddy.components.PullToRefreshLayout
import com.memeusix.budgetbuddy.navigation.BottomNavRoute
import com.memeusix.budgetbuddy.navigation.BudgetTransactionScreenRoute
import com.memeusix.budgetbuddy.ui.dashboard.budget.viewModel.BudgetViewModel
import com.memeusix.budgetbuddy.ui.dashboard.transactions.TransactionDetailsDialog
import com.memeusix.budgetbuddy.ui.dashboard.transactions.TransactionListContent
import com.memeusix.budgetbuddy.ui.dashboard.transactions.viewmodel.TransactionViewModel
import com.memeusix.budgetbuddy.utils.NavigationRequestKeys
import com.memeusix.budgetbuddy.utils.dynamicImePadding
import com.memeusix.budgetbuddy.utils.getViewModelStoreOwner
import com.memeusix.budgetbuddy.utils.handleApiResponse
import com.memeusix.budgetbuddy.utils.toastUtils.CustomToast
import com.memeusix.budgetbuddy.utils.toastUtils.CustomToastModel

@Composable
fun BudgetTransactionScreen(
    navController: NavHostController,
    args: BudgetTransactionScreenRoute,
    budgetViewModel: BudgetViewModel = hiltViewModel(navController.getViewModelStoreOwner()),
    transactionViewModel: TransactionViewModel = hiltViewModel(navController.getViewModelStoreOwner())
) {
    LaunchedEffect(Unit) {
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
        Log.e(
            "TransactionScreen",
            "TransactionScreen: ${transactionViewModel.transactionQueryParameters.value}"
        )
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

    Scaffold(topBar = {
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
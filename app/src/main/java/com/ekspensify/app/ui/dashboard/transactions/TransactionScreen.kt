package com.ekspensify.app.ui.dashboard.transactions

import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.ekspensify.app.R
import com.ekspensify.app.components.EmptyView
import com.ekspensify.app.components.PagingListLoader
import com.ekspensify.app.components.PullToRefreshLayout
import com.ekspensify.app.components.ShowLoader
import com.ekspensify.app.data.ApiResponse
import com.ekspensify.app.data.model.responseModel.TransactionResponseModel
import com.ekspensify.app.navigation.FilterScreenRoute
import com.ekspensify.app.ui.dashboard.transactions.components.TransactionListItem
import com.ekspensify.app.ui.dashboard.transactions.data.SelectedFilterModel
import com.ekspensify.app.ui.dashboard.transactions.filterComponets.SearchBar
import com.ekspensify.app.ui.dashboard.transactions.viewmodel.TransactionViewModel
import com.ekspensify.app.ui.theme.extendedColors
import com.ekspensify.app.utils.NavigationRequestKeys
import com.ekspensify.app.utils.handleApiResponse
import com.ekspensify.app.utils.singleClick
import com.ekspensify.app.utils.toastUtils.CustomToastModel

@Composable
fun TransactionScreen(
    navController: NavHostController,
    transactionViewModel: TransactionViewModel,
) {
    val transactions = transactionViewModel.getTransactions().collectAsLazyPagingItems()


    val dialogState = transactionViewModel.transactionDetails.collectAsStateWithLifecycle()
    val showDialog = rememberUpdatedState(dialogState.value != null)
    val deleteTransaction by transactionViewModel.deleteTransaction.collectAsStateWithLifecycle()
    val toastState = remember { mutableStateOf<CustomToastModel?>(null) }
    val searchState = remember { mutableStateOf("") }
    val isLoading = deleteTransaction is ApiResponse.Loading

    val selectedFilterModel by transactionViewModel.userSelectedFilters.collectAsStateWithLifecycle()

    LaunchedEffect(deleteTransaction) {
        handleApiResponse(
            response = deleteTransaction,
            toastState = toastState,
            navController = navController,
            onSuccess = {
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    NavigationRequestKeys.REFRESH_TRANSACTION, true
                )
            }
        )
    }
    if (showDialog.value) {
        TransactionDetailsDialog(
            transaction = dialogState.value,
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

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SearchBar(
            searchState = searchState,
            onSearch = {
                transactionViewModel.updateTransactionQuery(transactionViewModel.transactionQueryParameters.value.copy(
                    q = searchState.value.ifEmpty { null }
                ))
            },
            onFilterClick = singleClick {
                navController.navigate(
                    FilterScreenRoute
                )
            },
            isFilterApplied = selectedFilterModel != SelectedFilterModel()
        )
        PullToRefreshLayout(
            onRefresh = transactionViewModel::refreshTransaction,
            modifier = Modifier.weight(1f)
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
                onClick = { transaction ->
                    transactionViewModel.openDialog(transaction)
                }
            )
            ShowLoader(isLoading)
        }
    }
}


@Composable
fun TransactionListContent(
    modifier: Modifier,
    transactions: LazyPagingItems<TransactionResponseModel>,
    isEnable: Boolean = true,
    onClick: (TransactionResponseModel?) -> Unit,
) {
    val lazyState = rememberLazyListState()
    LazyColumn(
        modifier = modifier,
        state = lazyState,
        flingBehavior = ScrollableDefaults.flingBehavior(),
        userScrollEnabled = true
    ) {
        items(transactions.itemCount, key = transactions.itemKey { it.id!! }) { index ->
            val transaction = transactions[index]
            TransactionListItem(
                transaction = transaction,
                onClick = onClick,
                isEnable = isEnable,
                modifier = Modifier.padding(
                    vertical = 15.dp,
                    horizontal = 20.dp
                ),
            )
            HorizontalDivider(
                color = MaterialTheme.extendedColors.primaryBorder
            )
        }
        transactions.apply {
            item {
                PagingListLoader(loadState)
            }
        }
    }
}

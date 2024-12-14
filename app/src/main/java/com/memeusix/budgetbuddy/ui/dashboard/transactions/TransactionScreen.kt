package com.memeusix.budgetbuddy.ui.dashboard.transactions

import android.util.Log
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.memeusix.budgetbuddy.components.CustomListItem
import com.memeusix.budgetbuddy.data.model.responseModel.TransactionResponseModel
import com.memeusix.budgetbuddy.navigation.FilterScreenRoute
import com.memeusix.budgetbuddy.ui.categories.components.CategoryIcon
import com.memeusix.budgetbuddy.ui.categories.components.ShowIconLoader
import com.memeusix.budgetbuddy.ui.dashboard.transactions.data.SelectedFilterModel
import com.memeusix.budgetbuddy.ui.dashboard.transactions.filterComponets.SearchBar
import com.memeusix.budgetbuddy.ui.dashboard.transactions.viewmodel.TransactionViewModel
import com.memeusix.budgetbuddy.ui.theme.Green100
import com.memeusix.budgetbuddy.ui.theme.Green80
import com.memeusix.budgetbuddy.ui.theme.Red100
import com.memeusix.budgetbuddy.ui.theme.Red80
import com.memeusix.budgetbuddy.ui.theme.extendedColors
import com.memeusix.budgetbuddy.utils.TransactionType
import com.memeusix.budgetbuddy.utils.formatDateTime
import com.memeusix.budgetbuddy.utils.formatRupees
import com.memeusix.budgetbuddy.utils.handleApiResponse
import com.memeusix.budgetbuddy.utils.singleClick
import com.memeusix.budgetbuddy.utils.toastUtils.CustomToastModel

@Composable
fun TransactionScreen(
    navController: NavHostController,
    transactionViewModel: TransactionViewModel,
) {
    val transactions = transactionViewModel.getTransactions().collectAsLazyPagingItems()
    val lazyState = rememberLazyListState()
    val dialogState = transactionViewModel.transactionDetails.collectAsStateWithLifecycle()
    val showDialog = rememberUpdatedState(dialogState.value != null)
    val deleteTransaction by transactionViewModel.deleteTransaction.collectAsStateWithLifecycle()
    val toastState = remember { mutableStateOf<CustomToastModel?>(null) }
    val searchState = remember { mutableStateOf("") }

    val selectedFilterModel by transactionViewModel.selectedFilterModel.collectAsStateWithLifecycle()


    LaunchedEffect(deleteTransaction) {
        Log.e("TransactionScreen", "TransactionScreen: ${transactionViewModel.filterState.value}")
        handleApiResponse(
            response = deleteTransaction,
            toastState = toastState,
            onSuccess = {
                transactionViewModel.updateFilter(
                    transactionViewModel.filterState.value.copy(
                        updateList = !(transactionViewModel.filterState.value.updateList)
                    )
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
                transactionViewModel.updateFilter(transactionViewModel.filterState.value.copy(
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
        LazyColumn(
            modifier = Modifier.weight(1f),
            state = lazyState,
            flingBehavior = ScrollableDefaults.flingBehavior(),
            userScrollEnabled = true
        ) {
            items(transactions.itemCount, key = transactions.itemKey { it.id!! }) { index ->
                val transaction = transactions[index]
                CustomListItem(
                    modifier = Modifier.padding(
                        vertical = 15.dp,
                        horizontal = 20.dp
                    ),
                    title = transaction?.category?.name.orEmpty(),
                    subtitle = transaction?.account?.name.orEmpty(),
                    desc = transaction?.note.orEmpty(),
                    leadingContent = {
                        CategoryIcon(transaction?.category?.icon)
                    },
                    trailingContent = {
                        TransactionTrailingContent(transaction)
                    }, onClick = {
                        transactionViewModel.openDialog(transaction)
                    }
                )
                HorizontalDivider(
                    color = MaterialTheme.extendedColors.primaryBorder
                )
            }
            transactions.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item {
                            ShowBottomLoader()
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        item {
                            ShowBottomLoader()
                        }
                    }

                    loadState.prepend is LoadState.Error -> {
                        item {
                            ShowBottomLoader()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShowBottomLoader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        ShowIconLoader(Modifier)
    }
}

@Composable
fun TransactionTrailingContent(transaction: TransactionResponseModel?) {
    val amount = remember(transaction?.type, transaction?.amount) {
        if (transaction?.type == TransactionType.CREDIT.toString()) {
            "+₹${transaction.amount?.formatRupees()}"
        } else {
            "-₹${transaction?.amount?.formatRupees()}"
        }
    }
    val dataTime = remember(transaction?.createdAt) { transaction?.createdAt.formatDateTime() }

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
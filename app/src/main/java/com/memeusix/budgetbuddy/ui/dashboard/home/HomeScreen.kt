package com.memeusix.budgetbuddy.ui.dashboard.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.components.HorizontalSpace
import com.memeusix.budgetbuddy.components.PullToRefreshLayout
import com.memeusix.budgetbuddy.components.ShowLoader
import com.memeusix.budgetbuddy.components.VerticalSpace
import com.memeusix.budgetbuddy.data.ApiResponse
import com.memeusix.budgetbuddy.navigation.AccountScreenRoute
import com.memeusix.budgetbuddy.navigation.AutoTrackingScreenRoute
import com.memeusix.budgetbuddy.navigation.PendingTransactionRoute
import com.memeusix.budgetbuddy.ui.dashboard.home.bottomSheets.HomeInsightsBottomSheetDialog
import com.memeusix.budgetbuddy.ui.dashboard.home.components.AutoTrackingCard
import com.memeusix.budgetbuddy.ui.dashboard.home.components.CategoryInsightCard
import com.memeusix.budgetbuddy.ui.dashboard.home.components.HomeAppBar
import com.memeusix.budgetbuddy.ui.dashboard.home.components.HorizontalAccountListItem
import com.memeusix.budgetbuddy.ui.dashboard.home.components.InsightsCategoryItem
import com.memeusix.budgetbuddy.ui.dashboard.home.components.TotalBalanceCard
import com.memeusix.budgetbuddy.ui.dashboard.home.viewModel.HomeViewModel
import com.memeusix.budgetbuddy.ui.dashboard.transactions.data.DateRange
import com.memeusix.budgetbuddy.utils.CategoryType
import com.memeusix.budgetbuddy.utils.getViewModelStoreOwner

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(navController.getViewModelStoreOwner())
) {
    val isAutoTrackingEnable by homeViewModel.spUtilsManager.isAutoTrackingEnable.collectAsStateWithLifecycle()
    val getCategoryInsights by homeViewModel.getCategoryInsight.collectAsStateWithLifecycle()
    val getAcSummary by homeViewModel.getSummary.collectAsStateWithLifecycle()
    val getAccounts by homeViewModel.spUtilsManager.accountData.collectAsStateWithLifecycle()
    val selectedQuery by homeViewModel.insightsQueries.collectAsStateWithLifecycle()
    val user by homeViewModel.spUtilsManager.user.collectAsStateWithLifecycle()

    val isLoading =
        getCategoryInsights is ApiResponse.Loading || getAcSummary is ApiResponse.Loading

    // show DateRange Options BottomSheet
    var showDateRangeOptions by remember { mutableStateOf(false) }
    if (showDateRangeOptions) {
        HomeInsightsBottomSheetDialog(
            selectedRange = selectedQuery.dateRange,
            options = DateRange.getEntriesWithNoCustom(),
            title = stringResource(R.string.time_period),
            onClick = {
                homeViewModel.setInsightsQuery(
                    selectedQuery.copy(dateRange = it)
                )
                homeViewModel.fetchDashBoardInsights()
                showDateRangeOptions = false
            },
            onDismiss = { showDateRangeOptions = false }
        )
    }

    // show CategoryType Options
    var showCategoryTypeOptions by remember { mutableStateOf(false) }
    if (showCategoryTypeOptions) {
        HomeInsightsBottomSheetDialog(
            selectedRange = selectedQuery.type,
            options = CategoryType.getEntriesWithNoBoth(),
            title = stringResource(R.string.category_type),
            onClick = {
                homeViewModel.setInsightsQuery(
                    selectedQuery.copy(type = it)
                )
                homeViewModel.getCategoryInsights()
                showCategoryTypeOptions = false
            },
            onDismiss = { showCategoryTypeOptions = false }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HomeAppBar(
            user = user,
            selectedDateRange = selectedQuery.dateRange,
            onClick = {
                showDateRangeOptions = true
            }
        )
        PullToRefreshLayout(
            modifier = Modifier.weight(1f),
            onRefresh = {
                homeViewModel.fetchDashBoardInsights()
            }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                item {
                    AutoTrackingCard(
                        isEnable = isAutoTrackingEnable,
                        onEnableClick = { navController.navigate(AutoTrackingScreenRoute) },
                        onPendingTnxClick = { navController.navigate(PendingTransactionRoute) }
                    )
                }
                item {
                    val summaryData = getAcSummary.data
                    this@Column.AnimatedVisibility(
                        visible = summaryData != null
                    ) {
                        TotalBalanceCard(
                            total = summaryData?.total.toString(),
                            income = summaryData?.credit?.toString() ?: "0",
                            expense = summaryData?.debit?.toString() ?: "0",
                            onClick = {
                                navController.navigate(AccountScreenRoute(isFromProfile = true))
                            }
                        )
                    }
                }
                item {
                    val accounts = getAccounts?.accounts.orEmpty()
                    this@Column.AnimatedVisibility(
                        visible = accounts.isNotEmpty()
                    ) {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            item { HorizontalSpace() }
                            items(accounts) { acc ->
                                HorizontalAccountListItem(acc)
                            }
                            item { HorizontalSpace() }
                        }
                    }
                }
                val categoryInsights = getCategoryInsights.data.orEmpty()
                item {
                    this@Column.AnimatedVisibility(
                        visible = categoryInsights.isNotEmpty()
                    ) {
                        CategoryInsightCard(
                            selectedType = selectedQuery.type.displayName,
                            onClick = {
                                showCategoryTypeOptions = true
                            },
                            categoryInsights
                        )
                    }
                }
                items(categoryInsights) { item ->
                    InsightsCategoryItem(item)
                }
                item {
                    VerticalSpace(20.dp)
                }
            }

        }
    }
    ShowLoader(isLoading, true)
}

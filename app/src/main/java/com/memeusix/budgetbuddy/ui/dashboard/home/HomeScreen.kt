package com.memeusix.budgetbuddy.ui.dashboard.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.memeusix.budgetbuddy.components.HorizontalSpace
import com.memeusix.budgetbuddy.navigation.AutoTrackingScreenRoute
import com.memeusix.budgetbuddy.navigation.PendingTransactionRoute
import com.memeusix.budgetbuddy.ui.acounts.components.AmountText
import com.memeusix.budgetbuddy.ui.dashboard.home.components.AutoTrackingCard
import com.memeusix.budgetbuddy.ui.dashboard.home.components.CategoryInsightCard
import com.memeusix.budgetbuddy.ui.dashboard.home.components.HomeAppBar
import com.memeusix.budgetbuddy.ui.dashboard.home.components.HorizontalAccountListItem
import com.memeusix.budgetbuddy.ui.dashboard.home.components.TotalBalanceCard
import com.memeusix.budgetbuddy.ui.dashboard.home.viewModel.HomeViewModel
import com.memeusix.budgetbuddy.utils.CustomCornerShape

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val isAutoTrackingEnable by homeViewModel.spUtilsManager.isAutoTrackingEnable.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HomeAppBar()
        LazyColumn(
            modifier = Modifier
                .weight(1f),
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
                TotalBalanceCard()
            }
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    item { HorizontalSpace() }
                    items(count = 10) {
                        HorizontalAccountListItem()
                    }
                    item { HorizontalSpace() }
                }
            }
            item {
                CategoryInsightCard()
            }
            items(10) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(
                        Modifier
                            .fillMaxHeight()
                            .width(3.dp)
                            .clip(CustomCornerShape(topRight = 20.dp, bottomRight = 20.dp))
                            .background(Color.Gray),
                    )
                    HorizontalSpace(8.dp)
                    Text(
                        "Education", style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Spacer(Modifier.weight(1f))
                    AmountText("351351", size = 16.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}


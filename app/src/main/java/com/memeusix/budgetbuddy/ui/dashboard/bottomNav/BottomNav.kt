package com.memeusix.budgetbuddy.ui.dashboard.bottomNav

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.memeusix.budgetbuddy.data.ApiResponse
import com.memeusix.budgetbuddy.ui.components.AppBar
import com.memeusix.budgetbuddy.ui.dashboard.bottomNav.components.ActionButton
import com.memeusix.budgetbuddy.ui.dashboard.bottomNav.components.BottomBarContent
import com.memeusix.budgetbuddy.ui.dashboard.bottomNav.components.ExpandableFab
import com.memeusix.budgetbuddy.ui.dashboard.budget.BudgetScreen
import com.memeusix.budgetbuddy.ui.dashboard.home.HomeScreen
import com.memeusix.budgetbuddy.ui.dashboard.profile.ProfileScreen
import com.memeusix.budgetbuddy.ui.dashboard.profile.ProfileViewModel
import com.memeusix.budgetbuddy.ui.dashboard.transactions.TransactionScreen
import com.memeusix.budgetbuddy.ui.theme.Violet5
import com.memeusix.budgetbuddy.ui.theme.Yellow20
import com.memeusix.budgetbuddy.utils.SpUtils


@Composable
fun BottomNav(
    navController: NavController,
    spUtils: SpUtils,
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {
    var currentIndex by rememberSaveable { mutableIntStateOf(0) }
    var isFabExpanded by rememberSaveable { mutableStateOf(false) }

    val items = remember {
        listOf(
            BottomNavItem.Home,
            BottomNavItem.Transaction,
            BottomNavItem.Action,
            BottomNavItem.Budget,
            BottomNavItem.Profile
        )
    }


    LaunchedEffect(Unit) {
        profileViewModel.getMe.collect { response ->
            if (response is ApiResponse.Success) {
                spUtils.user = response.data
            }
        }
    }

    Scaffold(
        topBar = {
            key(currentIndex) {
                AppBar(
                    heading = items[currentIndex].label,
                    navController = navController,
                    elevation = false,
                    isBackNavigation = false
                )
            }
        },
        bottomBar = {
            BottomBar(
                items = items,
                isFabExpended = isFabExpanded,
                currentIndex = currentIndex,
                onItemClick = { index ->
                    currentIndex = index
                    if (isFabExpanded) {
                        isFabExpanded = false
                    }
                },
                onFabClick = { isFabExpanded = !isFabExpanded }
            )
        },
        floatingActionButton = {
            ExpandableFab(
                isFabExpanded = isFabExpanded
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { paddingValues ->
        ContentView(
            paddingValue = paddingValues,
            isFabExpended = isFabExpanded,
            onFabChange = { isFabExpanded = it },
            currentItem = items[currentIndex],
            navController = navController
        )
    }
}

@Composable
fun BottomBar(
    items: List<BottomNavItem>,
    currentIndex: Int,
    isFabExpended: Boolean,
    onItemClick: (Int) -> Unit,
    onFabClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Violet5)
            .wrapContentHeight(),
    ) {
        BottomBarContent(
            items = items,
            currentIndex = currentIndex,
            onItemClick = onItemClick,
        )

        ActionButton(
            isFabExpended = isFabExpended,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(x = 0.dp, y = (-8).dp),
            onFabClicked = onFabClick
        )
    }
}


@Composable
private fun ContentView(
    paddingValue: PaddingValues,
    isFabExpended: Boolean,
    onFabChange: (Boolean) -> Unit,
    currentItem: BottomNavItem,
    navController: NavController
) {
    Surface(
        color = Color.Transparent,
        modifier = Modifier
            .padding(paddingValue)
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.background,
                        Violet5
                    ),
                )
            )
            .pointerInput(isFabExpended) {
                detectTapGestures {
                    if (isFabExpended) {
                        onFabChange(false)
                    }
                }
            }
    ) {
        when (currentItem) {
            is BottomNavItem.Home -> HomeScreen(navController)
            is BottomNavItem.Transaction -> TransactionScreen(navController)
            is BottomNavItem.Budget -> BudgetScreen(navController)
            is BottomNavItem.Profile -> ProfileScreen(navController)
            is BottomNavItem.Action -> Unit
        }
    }

}






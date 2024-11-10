package com.memeusix.budgetbuddy.ui.dashboard.bottomNav

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.memeusix.budgetbuddy.ui.components.AppBar
import com.memeusix.budgetbuddy.ui.dashboard.home.HomeScreen
import com.memeusix.budgetbuddy.ui.dashboard.user.UsersScreen
import com.memeusix.budgetbuddy.ui.theme.Light100
import com.memeusix.budgetbuddy.ui.theme.Light20
import com.memeusix.budgetbuddy.ui.theme.Typography
import com.memeusix.budgetbuddy.ui.theme.Violet100


@Composable
fun BottomNav(navController: NavController) {
    var isFabExpended by remember { mutableStateOf(false) }
    var currentIndex by remember { mutableIntStateOf(0) }
    val items = listOf(
        BottomNavItem.Home,
//        BottomNavItem.Transaction,
        BottomNavItem.Action,
//        BottomNavItem.Budget,
        BottomNavItem.Profile
    )
    Scaffold(
        topBar = {
            AppBar(
                heading = items[currentIndex].label,
                navController = navController,
                elevation = true,
                isBackNavigation = false
            )
        },
        bottomBar = {
            BottomBar(
                items = items,
                currentIndex = currentIndex,
                isFabExpended = isFabExpended,
                onItemClick = { index ->
                    currentIndex = index
                    if (isFabExpended) {
                        isFabExpended = false
                    }
                },
                onFabClick = { isFabExpended = !isFabExpended }
            )
        },
        floatingActionButton = {
            ExpandableFab(
                isFabExpanded = isFabExpended
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { paddingValues ->
        Surface(
            color = Light100,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .pointerInput(isFabExpended) {
                    // Detect touch events outside the FAB
                    detectTapGestures {
                        if (isFabExpended) {
                            isFabExpended = false
                        }
                    }
                }
        ) {
            // Render screens based on the current selected index
            when (items[currentIndex]) {
                is BottomNavItem.Home -> HomeScreen(navController)
//                is BottomNavItem.Transaction -> TransactionScreen(navController)
//                is BottomNavItem.Budget -> BudgetScreen(navController)
                is BottomNavItem.Profile -> UsersScreen(navController)
                is BottomNavItem.Action -> {}
            }
        }
    }
}

@Composable
fun BottomBar(
    items: List<BottomNavItem>,
    currentIndex: Int,
    isFabExpended: Boolean,
    onItemClick: (Int) -> Unit,
    onFabClick: () -> Unit
) {
    Surface(
        shadowElevation = 1.dp,
        color = Light100,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items.forEachIndexed { index, item ->
                if (item == BottomNavItem.Action) {
                    ActionButton(
                        isFabExpended,
                        Modifier.wrapContentSize(),
                        onFabClicked = onFabClick
                    )
                } else {
                    BottomNavOptions(
                        item = item,
                        isSelected = index == currentIndex,
                        modifier = Modifier.weight(1f),
                        onClick = { onItemClick(index) }
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavOptions(
    item: BottomNavItem,
    isSelected: Boolean,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.then(
            Modifier
                .padding(vertical = 14.dp)
                .clickable(
                    indication = null,
                    interactionSource = null,
                    onClick = onClick
                )

        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painterResource(item.icon),
            contentDescription = item.label,
            tint = if (isSelected) Violet100 else Light20,
            modifier = Modifier.size(30.dp)
        )
        Text(
            item.label,
            style = Typography.labelSmall.copy(fontSize = 10.sp),
            color = if (isSelected) Violet100 else Light20
        )
    }
}



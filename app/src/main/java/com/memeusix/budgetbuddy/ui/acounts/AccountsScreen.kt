package com.memeusix.budgetbuddy.ui.acounts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastSumBy
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.data.ApiResponse
import com.memeusix.budgetbuddy.data.model.responseModel.AccountResponseModel
import com.memeusix.budgetbuddy.navigation.AccountScreenRoute
import com.memeusix.budgetbuddy.navigation.CreateAccountScreenRoute
import com.memeusix.budgetbuddy.ui.acounts.components.AccountListItem
import com.memeusix.budgetbuddy.ui.acounts.components.AmountText
import com.memeusix.budgetbuddy.ui.acounts.components.EmptyListView
import com.memeusix.budgetbuddy.ui.acounts.viewModel.AccountViewModel
import com.memeusix.budgetbuddy.components.AccountCardToggleWithHorizontalDashLine
import com.memeusix.budgetbuddy.components.AppBar
import com.memeusix.budgetbuddy.components.FilledButton
import com.memeusix.budgetbuddy.ui.loader.ShowLoader
import com.memeusix.budgetbuddy.ui.theme.Dark10
import com.memeusix.budgetbuddy.ui.theme.Light20
import com.memeusix.budgetbuddy.ui.theme.Light40
import com.memeusix.budgetbuddy.utils.AccountType
import com.memeusix.budgetbuddy.utils.NavigationRequestKeys
import com.memeusix.budgetbuddy.utils.singleClick
import com.memeusix.budgetbuddy.utils.toJson
import com.memeusix.budgetbuddy.utils.toastUtils.CustomToast
import com.memeusix.budgetbuddy.utils.toastUtils.CustomToastModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsScreen(
    navController: NavController,
    args: AccountScreenRoute,
    viewModel: AccountViewModel = hiltViewModel()
) {
    // All States
    val getAllAccounts by viewModel.getAllAccounts.collectAsStateWithLifecycle()
    val isLoading = getAllAccounts is ApiResponse.Loading
    var toastState by remember { mutableStateOf<CustomToastModel?>(null) }
    var selectedAccountType by remember { mutableStateOf(AccountType.BANK) }


    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    LaunchedEffect(savedStateHandle) {
        savedStateHandle?.getLiveData<Boolean>(NavigationRequestKeys.DELETE_OR_UPDATE_ACCOUNT)
            ?.observeForever { result ->
                if (result == true) {
                    viewModel.getAllAccounts()
                    savedStateHandle.remove<Boolean>(NavigationRequestKeys.DELETE_OR_UPDATE_ACCOUNT)
                }
            }

        if (getAllAccounts is ApiResponse.Success) {
            val bankAccountsExist =
                getAllAccounts.data?.any { it.type == AccountType.BANK.toString() } == true
            selectedAccountType = if (bankAccountsExist) AccountType.BANK else AccountType.WALLET
        }
    }

    // Main Ui
    Scaffold(
        topBar = {
            AppBar(
                heading = stringResource(R.string.accounts),
                navController = navController,
                isBackNavigation = args.isFromProfile,
                elevation = false
            )
        },
    ) { paddingValues ->
        PullToRefreshBox(
            isRefreshing = isLoading,
            onRefresh = viewModel::getAllAccounts,
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding()
                    .padding(
                        top = paddingValues.calculateTopPadding(),
                        start = 20.dp,
                        end = 20.dp,
                        bottom = 20.dp
                    )
                    .verticalScroll(rememberScrollState())
            ) {
                getAllAccounts.data?.let { accountList ->
                    if (accountList.isEmpty()) {
                        EmptyListView {
                            goToCreateAccount(navController, args, accountList)
                        }
                    } else {
                        Spacer(Modifier.height(39.dp))
                        TotalBalance(
                            accountList.fastSumBy { it.balance ?: 0 }.toString()
                        )
                        Spacer(Modifier.height(39.dp))
                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            AccountsCard(selectedAccountType = selectedAccountType,
                                accountList = accountList,
                                onSelectedChanged = { type ->
                                    selectedAccountType = type
                                },
                                onClick = { item ->
                                    navController.navigate(
                                        CreateAccountScreenRoute(
                                            accountResponseArgs = item.toJson(),
                                            accountList = accountList.toJson(),
                                            isFromProfile = args.isFromProfile
                                        )
                                    )
                                })
                        }
                        AddMoreBtn(args) {
                            goToCreateAccount(navController, args, accountList)
                        }
                    }
                }
            }
        }

    }

    // Custom Toast
    CustomToast(toastState) { toastState = null }

//    // Loader
    ShowLoader(isLoading)
}

private fun goToCreateAccount(
    navController: NavController,
    args: AccountScreenRoute,
    accountList: List<AccountResponseModel>,
) {
    navController.navigate(
        CreateAccountScreenRoute(
            accountResponseArgs = null,
            accountList = accountList.toJson(),
            isFromProfile = args.isFromProfile
        )
    )
}

// Top Total Balance View
@Composable
private fun TotalBalance(
    balance: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()
    ) {
        AmountText(balance)
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Total Balance",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}


// Account Card View
@Composable
private fun AccountsCard(
    selectedAccountType: AccountType,
    accountList: List<AccountResponseModel>,
    onSelectedChanged: (AccountType) -> Unit,
    onClick: (AccountResponseModel) -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .border(
                width = 1.dp, color = Dark10, RoundedCornerShape(16.dp)
            )
            .animateContentSize(),
    ) {
        AccountCardToggleWithHorizontalDashLine(
            selectedAccountType,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
            onTypeChange = onSelectedChanged
        )
        AccountList(accountList, selectedAccountType, onClick)
        AccountCardFooter(accountList, selectedAccountType)
    }
}

@Composable
private fun AccountCardFooter(
    accountList: List<AccountResponseModel>, selectedAccountType: AccountType
) {
    val filteredList = accountList.filter { it.type == selectedAccountType.toString() }
    AccountListItem(isListItem = false,
        AccountResponseModel(
            balance = filteredList.fastSumBy { it.balance ?: 0 },
            name = "Total",
        ),
        listCountString = "${filteredList.size} ${selectedAccountType.getVal()} Accounts",
        onClick = {})
}

@Composable
private fun AccountList(
    accountList: List<AccountResponseModel>,
    selectedAccountType: AccountType,
    onClick: (AccountResponseModel) -> Unit
) {
    val filteredList = accountList.filter { it.type == selectedAccountType.toString() }
    LazyColumn {
        items(filteredList, key = {
            it.id ?: 0
        }) { account ->
            AccountListItem(isListItem = true, account, onClick = singleClick { onClick(account) })
            HorizontalDivider(color = Dark10)
        }
    }
}

@Composable
private fun AddMoreBtn(args: AccountScreenRoute, onClick: () -> Unit) {
    val text = if (args.isFromProfile) "Add" else "Continue"
    FilledButton(text = text,
        textModifier = Modifier.padding(vertical = 17.dp),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.padding(top = 10.dp),
        onClick = singleClick { onClick() })
}

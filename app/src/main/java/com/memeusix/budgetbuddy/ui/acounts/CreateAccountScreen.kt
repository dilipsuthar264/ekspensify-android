package com.memeusix.budgetbuddy.ui.acounts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.data.ApiResponse
import com.memeusix.budgetbuddy.data.model.TextFieldStateModel
import com.memeusix.budgetbuddy.data.model.requestModel.AccountRequestModel
import com.memeusix.budgetbuddy.data.model.responseModel.AccountResponseModel
import com.memeusix.budgetbuddy.navigation.BottomNavRoute
import com.memeusix.budgetbuddy.navigation.CreateAccountScreenRoute
import com.memeusix.budgetbuddy.ui.acounts.createAccountComponents.AccountTypeCard
import com.memeusix.budgetbuddy.ui.acounts.createAccountComponents.BalanceView
import com.memeusix.budgetbuddy.ui.acounts.dialog.DeleteAccountDialog
import com.memeusix.budgetbuddy.ui.acounts.model.BankModel
import com.memeusix.budgetbuddy.ui.acounts.viewModel.AccountViewModel
import com.memeusix.budgetbuddy.ui.components.AppBar
import com.memeusix.budgetbuddy.ui.components.CustomOutlineTextField
import com.memeusix.budgetbuddy.ui.components.FilledButton
import com.memeusix.budgetbuddy.ui.components.TextFieldType
import com.memeusix.budgetbuddy.ui.loader.ShowLoader
import com.memeusix.budgetbuddy.ui.theme.Light20
import com.memeusix.budgetbuddy.utils.AccountType
import com.memeusix.budgetbuddy.utils.NavigationRequestKeys
import com.memeusix.budgetbuddy.utils.fromJson
import com.memeusix.budgetbuddy.utils.singleClick
import com.memeusix.budgetbuddy.utils.toastUtils.CustomToast
import com.memeusix.budgetbuddy.utils.toastUtils.CustomToastModel

@Composable
fun CreateAccountScreen(
    navController: NavController,
    args: CreateAccountScreenRoute?,
    viewModel: AccountViewModel = hiltViewModel()
) {

    // States
    val scrollState = rememberScrollState()
    var toastState by remember { mutableStateOf<CustomToastModel?>(null) }
    var deleteDialogState by remember { mutableStateOf(false) }

    val argsAccountDetails = remember { args?.accountResponseArgs.fromJson<AccountResponseModel>() }

    val balanceState = remember { mutableStateOf(TextFieldStateModel()) }
    var selectedAccountType by rememberSaveable { mutableStateOf(AccountType.BANK) }
    val bankList = remember { mutableStateListOf(*BankModel.getBanks().toTypedArray()) }
    val walletList = remember { mutableStateListOf(*BankModel.getWallet().toTypedArray()) }
    var selectedBank by remember { mutableStateOf<BankModel?>(null) }
    var selectedWallet by remember { mutableStateOf<BankModel?>(null) }

    // Api States
    val createAccountResponse by viewModel.createAccount.collectAsState()
    val updateAccountResponse by viewModel.updateAccount.collectAsState()
    val deleteAccountResponse by viewModel.deleteAccount.collectAsState()

    // Initial Data Set
    LaunchedEffect(Unit) {
        val accountLists = args?.accountList.fromJson<List<AccountResponseModel>>()
        val accountIconSlugSet = accountLists?.map { it.icon }?.toSet() ?: emptySet()

        argsAccountDetails?.let { details ->
            balanceState.value = balanceState.value.copy(
                text = (details.balance ?: 0).toString()
            )
            selectedAccountType = AccountType.valueOf(details.type ?: "BANK")
            selectedWallet = walletList.find { it.iconSlug == details.icon }
            selectedBank = bankList.find { it.iconSlug == details.icon }
        }

        val updateList: (List<BankModel>) -> Unit = { list ->
            list.forEach {
                it.isEnable =
                    it.iconSlug !in accountIconSlugSet || (it == selectedBank) || (it == selectedWallet)
            }
        }
        updateList(walletList)
        updateList(bankList)

        if (argsAccountDetails == null) {
            selectedWallet = walletList.firstOrNull { it.isEnable }
            selectedBank = bankList.firstOrNull { it.isEnable }
        }
    }

    // Api Response Handling
    LaunchedEffect(createAccountResponse, updateAccountResponse, deleteAccountResponse) {
        // Handle createAccount Result
        when (createAccountResponse) {
            is ApiResponse.Success -> {
                createAccountResponse.data?.apply {
                    if (args?.isFromProfile == true) {
                        gotBackToAccountList(navController)
                    } else {
                        navController.navigate(
                            BottomNavRoute
                        ) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                }
            }

            is ApiResponse.Failure -> {
                createAccountResponse.errorResponse?.apply {
                    toastState = CustomToastModel(
                        message = this.message, isVisible = true
                    )
                }
            }

            else -> Unit
        }

        // Handle Update Account Response
        when (updateAccountResponse) {
            is ApiResponse.Success -> {
                updateAccountResponse.data?.apply {
                    gotBackToAccountList(navController)
                }
            }

            is ApiResponse.Failure -> {
                updateAccountResponse.errorResponse?.apply {
                    toastState = CustomToastModel(
                        message = this.message, isVisible = true
                    )
                }
            }

            else -> Unit
        }

        // Handle Delete Account Response
        when (deleteAccountResponse) {
            is ApiResponse.Success -> {
                deleteAccountResponse.data?.apply {
                    toastState = CustomToastModel(
                        message = "Account Deleted Successfully", isVisible = true
                    )
                    gotBackToAccountList(navController)
                }
            }

            is ApiResponse.Failure -> {
                deleteAccountResponse.errorResponse?.apply {
                    toastState = CustomToastModel(
                        message = this.message, isVisible = true
                    )
                }
            }

            else -> Unit
        }
    }

    // DeleteDialog
    if (deleteDialogState && argsAccountDetails?.id != null) {
        DeleteAccountDialog(argsAccountDetails, onDismiss = { delete ->
            if (delete) {
                viewModel.deleteAccount(argsAccountDetails.id!!)
            }
            deleteDialogState = false
        })
    }

    // Custom Toast
    CustomToast(toastState) {
        toastState = null
    }

    // Shows Loader
    ShowLoader(
        createAccountResponse is ApiResponse.Loading
                || updateAccountResponse is ApiResponse.Loading
                || deleteAccountResponse is ApiResponse.Loading
    )

    // Main Ui
    Scaffold(topBar = {
        AppBar(
            heading = stringResource(R.string.add_account),
            navController = navController,
            isBackNavigation = false,
            elevation = true
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    start = 20.dp,
                    end = 20.dp,
                )
                .imePadding()
                .verticalScroll(scrollState),
        ) {
            Spacer(Modifier.height(20.dp))
            BalanceView(balanceState, selectedAccount = when (selectedAccountType) {
                AccountType.BANK -> {
                    selectedBank
                }

                AccountType.WALLET -> {
                    selectedWallet
                }
            },
                showDelete = argsAccountDetails != null,
                onDeleteClick = {
                    deleteDialogState = !deleteDialogState
                })
            Spacer(Modifier.weight(1f))
            CustomOutlineTextField(
                state = balanceState,
                placeholder = stringResource(R.string.balance),
                isExpendable = false,
                maxLength = 6,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                type = TextFieldType.NUMBER
            )
            Spacer(Modifier.height(16.dp))
            AccountTypeCard(
                selectedAccountType,
                walletList,
                bankList,
                selectedWallet,
                selectedBank,
                onTypeChange = {
                    selectedAccountType = it
                },
                onSelectItem = { item ->
                    if (selectedAccountType == AccountType.WALLET) {
                        selectedWallet = item
                    } else {
                        selectedBank = item
                    }
                },
            )

            Text(
                stringResource(R.string.no_account_message),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Light20
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 20.dp),
            )
            Spacer(Modifier.weight(4f))
            FilledButton(
                text = if (argsAccountDetails == null) {
                    if (args?.isFromProfile == true) "Add" else "Continue"
                } else "Update",
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary,
                ),
                onClick = singleClick {
                    val accountRequestModel = AccountRequestModel(
                        name = when (selectedAccountType) {
                            AccountType.WALLET -> selectedWallet?.name
                            AccountType.BANK -> selectedBank?.name
                        },
                        type = selectedAccountType.toString(),
                        balance = balanceState.value.text.ifEmpty { "0" }.toInt(),
                    )
                    argsAccountDetails?.let {
                        viewModel.updateAccount(it.id!!, accountRequestModel)
                    } ?: run {
                        viewModel.createAccount(accountRequestModel)
                    }
                },
                shape = RoundedCornerShape(16.dp),
                textModifier = Modifier.padding(vertical = 17.dp),
                modifier = Modifier.padding(vertical = 16.dp),
                enabled = if (selectedAccountType == AccountType.WALLET) {
                    selectedWallet != null
                } else {
                    selectedBank != null
                }
            )
        }
    }
}

private fun gotBackToAccountList(navController: NavController) {
    navController.previousBackStackEntry?.savedStateHandle?.set(
        NavigationRequestKeys.DELETE_OR_UPDATE_ACCOUNT, true
    )
    navController.popBackStack()
}



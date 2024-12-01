package com.memeusix.budgetbuddy.ui.acounts

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.components.AppBar
import com.memeusix.budgetbuddy.components.CustomOutlineTextField
import com.memeusix.budgetbuddy.components.FilledButton
import com.memeusix.budgetbuddy.components.TextFieldType
import com.memeusix.budgetbuddy.components.VerticalSpace
import com.memeusix.budgetbuddy.data.ApiResponse
import com.memeusix.budgetbuddy.data.model.TextFieldStateModel
import com.memeusix.budgetbuddy.data.model.requestModel.AccountRequestModel
import com.memeusix.budgetbuddy.data.model.responseModel.AccountResponseModel
import com.memeusix.budgetbuddy.navigation.BottomNavRoute
import com.memeusix.budgetbuddy.navigation.CreateAccountScreenRoute
import com.memeusix.budgetbuddy.ui.acounts.components.AccountTypeGridItem
import com.memeusix.budgetbuddy.ui.acounts.components.AccountsCardView
import com.memeusix.budgetbuddy.ui.acounts.components.AmountText
import com.memeusix.budgetbuddy.ui.acounts.data.BankModel
import com.memeusix.budgetbuddy.ui.acounts.dialog.DeleteAccountDialog
import com.memeusix.budgetbuddy.ui.acounts.viewModel.AccountViewModel
import com.memeusix.budgetbuddy.ui.acounts.viewModel.CreateAccountState
import com.memeusix.budgetbuddy.components.ShowLoader
import com.memeusix.budgetbuddy.ui.theme.Light20
import com.memeusix.budgetbuddy.utils.AccountType
import com.memeusix.budgetbuddy.utils.NavigationRequestKeys
import com.memeusix.budgetbuddy.utils.dynamicPadding
import com.memeusix.budgetbuddy.utils.fromJson
import com.memeusix.budgetbuddy.utils.handleApiResponse
import com.memeusix.budgetbuddy.utils.singleClick
import com.memeusix.budgetbuddy.utils.toastUtils.CustomToast
import com.memeusix.budgetbuddy.utils.toastUtils.CustomToastModel

@Composable
fun CreateAccountScreen(
    navController: NavController,
    args: CreateAccountScreenRoute?,
    viewModel: AccountViewModel = hiltViewModel(),
    createAccountState: CreateAccountState = hiltViewModel(),
) {

    // Custom Toast
    val toastState = remember { mutableStateOf<CustomToastModel?>(null) }
    CustomToast(toastState)


    val balanceState = remember { mutableStateOf(TextFieldStateModel()) }

    var deleteDialogState by remember { mutableStateOf(false) }
    val selectedAccountType = rememberSaveable { mutableStateOf(AccountType.BANK) }

    val bankList = createAccountState.bankList.collectAsState()
    val walletList = createAccountState.walletList.collectAsState()
    val selectedBank = remember { mutableStateOf<BankModel?>(null) }
    val selectedWallet = remember { mutableStateOf<BankModel?>(null) }

    // navArgs
    val accountLists = remember { args?.accountList.fromJson<List<AccountResponseModel>>() }
    val argsAccountDetails = remember { args?.accountResponseArgs.fromJson<AccountResponseModel>() }

    // Api States
    val createAccountResponse by viewModel.createAccount.collectAsState()
    val updateAccountResponse by viewModel.updateAccount.collectAsState()
    val deleteAccountResponse by viewModel.deleteAccount.collectAsState()
    val isLoading = remember {
        derivedStateOf {
            createAccountResponse is ApiResponse.Loading || updateAccountResponse is ApiResponse.Loading || deleteAccountResponse is ApiResponse.Loading
        }
    }


    // Initial Data Set
    LaunchedEffect(Unit) {
        val initializedData = createAccountState.initialize(
            accountLists, argsAccountDetails
        )
        selectedBank.value = initializedData.first
        selectedWallet.value = initializedData.second

        argsAccountDetails?.let { details ->
            balanceState.value = balanceState.value.copy(
                text = (details.balance ?: 0).toString()
            )
            selectedAccountType.value = AccountType.valueOf(details.type ?: "BANK")
        }
    }

    // Api Response Handling
    LaunchedEffect(createAccountResponse, updateAccountResponse, deleteAccountResponse) {
        // Handle createAccount Result
        handleApiResponse(response = createAccountResponse,
            toastState = toastState,
            onSuccess = { data ->
                data?.let {
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
            })

        // Handle Update Account Response
        handleApiResponse(response = updateAccountResponse,
            toastState = toastState,
            onSuccess = { data ->
                data?.let {
                    gotBackToAccountList(navController)
                }
            })

        // Handle Delete Account Response
        handleApiResponse(response = deleteAccountResponse,
            toastState = toastState,
            onSuccess = { data ->
                toastState.value = CustomToastModel(
                    message = "Account Deleted Successfully", isVisible = true
                )
                gotBackToAccountList(navController)
            })
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

    // Main Ui
    Scaffold(
        topBar = {
            AppBar(
                heading = stringResource(R.string.add_account),
                navController = navController,
                isBackNavigation = true,
            )
        }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .dynamicPadding(paddingValues)
                .padding(horizontal = 20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
            ) {
                VerticalSpace(20.dp)
                BalanceView(balanceState,
                    selectedAccount = when (selectedAccountType.value) {
                        AccountType.BANK -> {
                            selectedBank.value
                        }

                        AccountType.WALLET -> {
                            selectedWallet.value
                        }
                    },
                    showDelete = argsAccountDetails != null && (accountLists?.size ?: 0) > 1,
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
                VerticalSpace(16.dp)
                AccountTypeCard(
                    selectedAccountType,
                    walletList.value,
                    bankList.value,
                    selectedWallet,
                    selectedBank,
                )
                Text(
                    stringResource(R.string.no_account_message),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Light20
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 20.dp),
                )
                VerticalSpace(60.dp)
                Spacer(Modifier.weight(4f))
            }
            SaveBtn(
                text = if (argsAccountDetails == null) (if (args?.isFromProfile == true) "Add" else "Continue") else "Update",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(vertical = 10.dp),
                isEnable = when (selectedAccountType.value) {
                    AccountType.WALLET -> selectedWallet.value != null
                    else -> selectedBank.value != null
                },
                onClick = singleClick {
                    val accountRequestModel = AccountRequestModel(
                        name = when (selectedAccountType.value) {
                            AccountType.WALLET -> selectedWallet.value?.name
                            AccountType.BANK -> selectedBank.value?.name
                        },
                        type = selectedAccountType.value.toString(),
                        balance = balanceState.value.text.ifEmpty { "0" }.toInt(),
                    )
                    argsAccountDetails?.let {
                        viewModel.updateAccount(it.id!!, accountRequestModel)
                    } ?: run {
                        viewModel.createAccount(accountRequestModel)
                    }
                },
            )
        }
        // showLoader
        ShowLoader(isLoading.value)
    }
}


@Composable
fun BalanceView(
    balanceState: MutableState<TextFieldStateModel>,
    selectedAccount: BankModel?,
    showDelete: Boolean,
    onDeleteClick: () -> Unit
) {
    AmountText(balanceState.value.text, Modifier)
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .height(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        selectedAccount?.icon?.let {
            Image(
                painterResource(it), contentDescription = null, modifier = Modifier.fillMaxHeight()
            )
        }
        Text(
            selectedAccount?.name ?: "No Account Selected",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold,
            ),
        )
        Spacer(modifier = Modifier.weight(1f))
        if (showDelete) {
            Image(
                painterResource(R.drawable.ic_delete),
                contentDescription = null,
                modifier = Modifier.clickable { onDeleteClick() })
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AccountTypeCard(
    selectedAccountType: MutableState<AccountType>,
    walletList: List<BankModel>,
    bankList: List<BankModel>,
    selectedWallet: MutableState<BankModel?>,
    selectedBank: MutableState<BankModel?>,
) {
    val displayedItems by remember(selectedAccountType, walletList, bankList) {
        derivedStateOf {
            when (selectedAccountType.value) {
                AccountType.WALLET -> walletList
                AccountType.BANK -> bankList
            }
        }
    }
    AccountsCardView(
        selectedAccountType = selectedAccountType.value,
        onTypeChange = { selectedAccountType.value = it }
    ) {
        val size = remember { mutableStateOf(IntSize.Zero) }
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .onSizeChanged { size.value = it }
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.spacedBy(5.dp),
        ) {

            val itemsPerRow = if (size.value.width == 0) 1 else (size.value.width / (70 * 3))
            val totalItems = displayedItems.size
            val remainder = totalItems % itemsPerRow

            displayedItems.forEach { item ->
                val accountItem =
                    item.copy(isSelected = item.iconSlug == selectedBank.value?.iconSlug || item.iconSlug == selectedWallet.value?.iconSlug)
                AccountTypeGridItem(
                    accountItem,
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .requiredWidthIn(min = 65.dp, max = 70.dp),
                    onClick = {
                        if (selectedAccountType.value == AccountType.WALLET) {
                            selectedWallet.value = item
                        } else {
                            selectedBank.value = item
                        }
                    },
                )
            }
            // Add placeholders to align the last row
            if (remainder != 0) {
                val placeholders = itemsPerRow - remainder
                repeat(placeholders) {
                    Spacer(
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .requiredWidthIn(min = 65.dp, max = 70.dp),
                    )
                }
            }
        }
    }
}


@Composable
fun SaveBtn(text: String, modifier: Modifier, isEnable: Boolean, onClick: () -> Unit) {
    FilledButton(
        text = text,
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        textModifier = Modifier.padding(vertical = 17.dp),
        modifier = modifier,
        enabled = isEnable
    )
}

private fun gotBackToAccountList(navController: NavController) {
    navController.previousBackStackEntry?.savedStateHandle?.set(
        NavigationRequestKeys.DELETE_OR_UPDATE_ACCOUNT, true
    )
    navController.popBackStack()
}



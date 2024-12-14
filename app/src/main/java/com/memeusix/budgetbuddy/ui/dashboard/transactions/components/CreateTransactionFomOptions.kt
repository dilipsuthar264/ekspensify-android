package com.memeusix.budgetbuddy.ui.dashboard.transactions.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.memeusix.budgetbuddy.components.CustomOutlineTextField
import com.memeusix.budgetbuddy.components.TextFieldType
import com.memeusix.budgetbuddy.data.ApiResponse
import com.memeusix.budgetbuddy.data.model.TextFieldStateModel
import com.memeusix.budgetbuddy.data.model.responseModel.AccountResponseModel
import com.memeusix.budgetbuddy.data.model.responseModel.AttachmentResponseModel
import com.memeusix.budgetbuddy.data.model.responseModel.CategoryResponseModel
import com.memeusix.budgetbuddy.imagePicker.ImagePickerBottomSheet
import com.memeusix.budgetbuddy.ui.categories.components.ShowIconLoader
import com.memeusix.budgetbuddy.ui.dashboard.transactions.bottomsheet.CategoryAndAccountBottomSheet
import com.memeusix.budgetbuddy.ui.dashboard.transactions.viewmodel.TransactionViewModel
import com.memeusix.budgetbuddy.utils.BottomSheetType
import com.memeusix.budgetbuddy.utils.TransactionType
import com.memeusix.budgetbuddy.utils.handleApiResponse
import com.memeusix.budgetbuddy.utils.openImageExternally
import com.memeusix.budgetbuddy.utils.toastUtils.CustomToastModel


@Composable
fun CreateTransactionFromOptions(
    isUpdate: Boolean,
    amountState: MutableState<TextFieldStateModel>,
    noteState: MutableState<TextFieldStateModel>,
    selectedCategory: MutableState<CategoryResponseModel>,
    selectedAccount: MutableState<AccountResponseModel>,
    selectedAttachment: MutableState<AttachmentResponseModel>,
    modifier: Modifier,
    transactionViewModel: TransactionViewModel,
    type: TransactionType,
    toastState: MutableState<CustomToastModel?>
) {
    val context = LocalContext.current
    val showImagePicker = remember { mutableStateOf(false) }
    val bottomSheetState = remember { mutableStateOf(false to BottomSheetType.CATEGORY) }
    val categories = remember {
        transactionViewModel.spUtils.categoriesData?.categories?.filter {
            it.type != type.getInvertedType().toString()
        }
            .orEmpty()
    }
    val accounts = remember { transactionViewModel.spUtils.accountData?.accounts.orEmpty() }

    val attachmentUploadState by transactionViewModel.uploadAttachment.collectAsStateWithLifecycle()
    val isAttachmentLoading = attachmentUploadState is ApiResponse.Loading


    // ImagePicker Bottom Sheet
    if (showImagePicker.value) {
        ImagePickerBottomSheet { imageUri ->
            showImagePicker.value = false
            if (imageUri != null) {
                transactionViewModel.uploadAttachment(imageUri)
            }
        }
    }

    LaunchedEffect(attachmentUploadState) {
        handleApiResponse(
            response = attachmentUploadState,
            toastState = toastState,
            onSuccess = { data ->
                data?.let {
                    selectedAttachment.value = it
                }
            }
        )
    }

    // Category and Account Bottom Sheet
    if (bottomSheetState.value.first) {
        CategoryAndAccountBottomSheet(
            selectedCategory = selectedCategory,
            selectedAccount = selectedAccount,
            categories = categories,
            accounts = accounts,
            type = bottomSheetState.value.second
        ) {
            bottomSheetState.value = bottomSheetState.value.copy(first = false)
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Amount Input
        CustomOutlineTextField(
            state = amountState,
            isExpendable = false,
            maxLength = 6,
            disable = isUpdate,
            placeholder = "Amount",
            type = TextFieldType.NUMBER,

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // Note Input
        CustomOutlineTextField(
            state = noteState,
            isExpendable = true,
            maxLength = 70,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            placeholder = "Description (Optional)"
        )

        // Selected Category
        SelectionOptions(
            disable = isUpdate,
            isCategory = true,
            selectedAccount = null,
            selectedCategory = selectedCategory.value
        ) {
            bottomSheetState.value = true to BottomSheetType.CATEGORY
        }

        // Selected Account
        SelectionOptions(
            disable = isUpdate,
            isCategory = false,
            selectedCategory = null,
            selectedAccount = selectedAccount.value
        ) {
            bottomSheetState.value = true to BottomSheetType.ACCOUNT
        }


        // Attachment View
        if (isAttachmentLoading) {
            ShowIconLoader(modifier.align(Alignment.CenterHorizontally))
        } else {
            AttachmentView(
                selectedAttachment,
                onClick = { isView ->
                    if (isView) {
                        selectedAttachment.value.let {
                            context.openImageExternally(it.path)
//                            navController.navigate(
//                                ImageWebViewRoute(
//                                    it.path
//                                )
//                            )
                        }
                    } else {
                        showImagePicker.value = true
                    }
                },
                onDelete = { selectedAttachment.value = AttachmentResponseModel() }
            )
        }
        Spacer(Modifier.height(70.dp))
    }
}


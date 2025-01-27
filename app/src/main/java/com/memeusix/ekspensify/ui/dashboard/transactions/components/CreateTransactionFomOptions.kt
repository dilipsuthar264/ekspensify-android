package com.memeusix.ekspensify.ui.dashboard.transactions.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import com.memeusix.ekspensify.components.CustomOutlineTextField
import com.memeusix.ekspensify.components.TextFieldRupeePrefix
import com.memeusix.ekspensify.components.TextFieldType
import com.memeusix.ekspensify.data.ApiResponse
import com.memeusix.ekspensify.data.model.TextFieldStateModel
import com.memeusix.ekspensify.data.model.responseModel.AccountResponseModel
import com.memeusix.ekspensify.data.model.responseModel.AttachmentResponseModel
import com.memeusix.ekspensify.data.model.responseModel.CategoryResponseModel
import com.memeusix.ekspensify.imagePicker.ImagePickerBottomSheet
import com.memeusix.ekspensify.ui.categories.components.ShowIconLoader
import com.memeusix.ekspensify.ui.dashboard.budget.bottomsheets.SelectAccountBottomSheet
import com.memeusix.ekspensify.ui.dashboard.budget.bottomsheets.SelectCategoryBottomSheet
import com.memeusix.ekspensify.ui.dashboard.transactions.viewmodel.TransactionViewModel
import com.memeusix.ekspensify.utils.BottomSheetSelectionType
import com.memeusix.ekspensify.utils.TransactionType
import com.memeusix.ekspensify.utils.handleApiResponse
import com.memeusix.ekspensify.utils.openImageExternally
import com.memeusix.ekspensify.utils.toastUtils.CustomToastModel


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
    val categories = remember(transactionViewModel.categoryList) {
        transactionViewModel.categoryList?.filter {
            it.type != type.getInvertedType().toString()
        }.orEmpty()
    }
    val accounts =
        remember(transactionViewModel.accountList) { transactionViewModel.accountList.orEmpty() }

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

    val showAccountBottomSheet = remember { mutableStateOf(false) }
    val showCategoryBottomSheet = remember { mutableStateOf(false) }

    // Account Bottom Sheet
    if (showAccountBottomSheet.value) {
        SelectAccountBottomSheet(
            accountList = accounts,
            selectedAccountId = selectedAccount.value.id,
            selectionType = BottomSheetSelectionType.SINGLE,
            onDismiss = { showAccountBottomSheet.value = false },
            onClick = { account ->
                account?.let {
                    selectedAccount.value = it
                    showAccountBottomSheet.value = false
                }
            })
    }

    // Category Bottom Sheet
    if (showCategoryBottomSheet.value) {
        SelectCategoryBottomSheet(
            categoryList = categories,
            selectedCategoryId = selectedCategory.value.id,
            selectionType = BottomSheetSelectionType.SINGLE,
            onDismiss = { showCategoryBottomSheet.value = false },
            onClick = { category ->
                category?.let {
                    selectedCategory.value = it
                    showCategoryBottomSheet.value = false
                }
            })
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
            prefix = { TextFieldRupeePrefix() },
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
            showCategoryBottomSheet.value = true
        }

        // Selected Account
        SelectionOptions(
            disable = isUpdate,
            isCategory = false,
            selectedCategory = null,
            selectedAccount = selectedAccount.value
        ) {
            showAccountBottomSheet.value = true
        }


        // Attachment View
        if (isAttachmentLoading) {
            ShowIconLoader(
                modifier
                    .size(32.dp)
                    .align(Alignment.CenterHorizontally))
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


package com.memeusix.budgetbuddy.ui.dashboard.transactions.components

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.memeusix.budgetbuddy.components.CustomOutlineTextField
import com.memeusix.budgetbuddy.components.TextFieldType
import com.memeusix.budgetbuddy.data.model.TextFieldStateModel
import com.memeusix.budgetbuddy.data.model.responseModel.AccountResponseModel
import com.memeusix.budgetbuddy.data.model.responseModel.CategoryResponseModel
import com.memeusix.budgetbuddy.imagePicker.ImagePickerBottomSheet
import com.memeusix.budgetbuddy.ui.dashboard.transactions.bottomsheet.CategoryAndAccountBottomSheet
import com.memeusix.budgetbuddy.ui.dashboard.transactions.viewmodel.TransactionViewModel
import com.memeusix.budgetbuddy.utils.BottomSheetType
import com.memeusix.budgetbuddy.utils.openImage


@Composable
fun CreateTransactionFromOptions(
    amountState: MutableState<TextFieldStateModel>,
    noteState: MutableState<TextFieldStateModel>,
    selectedCategory: MutableState<CategoryResponseModel>,
    selectedAccount: MutableState<AccountResponseModel>,
    selectedAttachment: MutableState<Uri?>,
    modifier: Modifier,
    transactionViewModel: TransactionViewModel
) {
    val context = LocalContext.current
    val showImagePicker = remember { mutableStateOf(false) }
    val bottomSheetState = remember { mutableStateOf(false to BottomSheetType.CATEGORY) }
    val categories = remember { transactionViewModel.spUtils.categoriesData?.categories.orEmpty() }
    val accounts = remember { transactionViewModel.spUtils.accountData?.accounts.orEmpty() }

    // ImagePicker Bottom Sheet
    if (showImagePicker.value) {
        ImagePickerBottomSheet { imageUri ->
            showImagePicker.value = false
            selectedAttachment.value = imageUri
        }
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
            isCategory = true,
            selectedAccount = null,
            selectedCategory = selectedCategory.value
        ) {
            bottomSheetState.value = true to BottomSheetType.CATEGORY
        }

        // Selected Account
        SelectionOptions(
            isCategory = false,
            selectedCategory = null,
            selectedAccount = selectedAccount.value
        ) {
            bottomSheetState.value = true to BottomSheetType.ACCOUNT
        }

        // Attachment View
        AttachmentView(
            selectedAttachment,
            onClick = { isView ->
                if (isView) {
                    selectedAttachment.value?.let { context.openImage(it) }
                } else {
                    showImagePicker.value = true
                }
            },
            onDelete = { selectedAttachment.value = null }
        )

        Spacer(Modifier.height(70.dp))
    }
}


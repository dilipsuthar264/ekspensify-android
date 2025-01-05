package com.memeusix.budgetbuddy.ui.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.components.AppBar
import com.memeusix.budgetbuddy.components.CustomOutlineTextField
import com.memeusix.budgetbuddy.components.FilledButton
import com.memeusix.budgetbuddy.components.ShowLoader
import com.memeusix.budgetbuddy.data.ApiResponse
import com.memeusix.budgetbuddy.data.model.TextFieldStateModel
import com.memeusix.budgetbuddy.data.model.responseModel.CategoryResponseModel
import com.memeusix.budgetbuddy.data.model.responseModel.CustomIconModel
import com.memeusix.budgetbuddy.ui.categories.components.CategoryTypeSelectionToggle
import com.memeusix.budgetbuddy.ui.categories.components.IconSelectionCard
import com.memeusix.budgetbuddy.ui.categories.components.SelectAnyIconText
import com.memeusix.budgetbuddy.ui.categories.components.ShowIconLoader
import com.memeusix.budgetbuddy.ui.categories.viewmodel.CategoryViewModel
import com.memeusix.budgetbuddy.utils.CategoryType
import com.memeusix.budgetbuddy.utils.ErrorReason
import com.memeusix.budgetbuddy.utils.dynamicImePadding
import com.memeusix.budgetbuddy.utils.getViewModelStoreOwner
import com.memeusix.budgetbuddy.utils.handleApiResponse
import com.memeusix.budgetbuddy.utils.handleApiResponseWithError
import com.memeusix.budgetbuddy.utils.toastUtils.CustomToast
import com.memeusix.budgetbuddy.utils.toastUtils.CustomToastModel

@Composable
fun CreateCategoryScreen(
    navController: NavHostController,
    viewModel: CategoryViewModel = hiltViewModel(navController.getViewModelStoreOwner())
) {

    // Custom Toast
    val toastState = remember { mutableStateOf<CustomToastModel?>(null) }
    CustomToast(toastState)


    val selectedCategoryType = remember { mutableStateOf(CategoryType.CREDIT) }
    val nameState = remember { mutableStateOf(TextFieldStateModel()) }
    val selectedIcon = remember { mutableStateOf(CustomIconModel()) }


    val createCategory by viewModel.createCategory.collectAsStateWithLifecycle()
    val customIconsState by viewModel.getCustomIcons.collectAsStateWithLifecycle()

    val isIconsLoading = remember { derivedStateOf { customIconsState is ApiResponse.Loading } }
    val icons = remember { derivedStateOf { customIconsState.data.orEmpty() } }

    val isLoading = createCategory is ApiResponse.Loading


    LaunchedEffect(createCategory, customIconsState) {
        // handle Create Category Response
        handleApiResponseWithError(
            response = createCategory,
            navController = navController,
            onFailure = { error ->
                error?.let {
                    if (it.reason == ErrorReason.CUSTOM_CATEGORY_ICON_NOT_FOUND
                        || it.reason == ErrorReason.ICON_NOT_FOUND
                    ) {
                        viewModel.getCustomIcons()
                        viewModel.resetCreateCategory()
                    }
                }
            },
            onSuccess = { data ->
                data?.let {
                    viewModel.resetCreateCategory()
                    viewModel.getCategories()
                    navController.popBackStack()
                }
            }
        )
        // handle Custom Icon Response
        handleApiResponse(
            response = customIconsState,
            toastState = toastState,
            navController = navController,
            onSuccess = { data ->
                data?.let {
                    if (it.isNotEmpty()) {
                        selectedIcon.value = it[0]
                    }
                }
            }
        )
    }

    // Main Ui
    Scaffold(topBar = {
        AppBar(
            heading = stringResource(R.string.create_category),
            elevation = false,
            isBackNavigation = true,
            navController = navController
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .dynamicImePadding(paddingValues)
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CategoryTypeSelectionToggle(selectedCategoryType)
                CustomOutlineTextField(
                    state = nameState,
                    placeholder = stringResource(R.string.name),
                    isExpendable = false,
                    maxLength = 10
                )

                if (isIconsLoading.value) {
                    ShowIconLoader(Modifier.size(32.dp).align(Alignment.CenterHorizontally))
                }
                if (icons.value.isNotEmpty()) {
                    IconSelectionCard(
                        icons.value,
                        selectedIcon
                    )
                }
                SelectAnyIconText(Modifier.align(Alignment.CenterHorizontally))
            }
            FilledButton(
                text = stringResource(R.string.add),
                enabled = nameState.value.text.trim()
                    .isNotEmpty() && selectedIcon.value.id != null,
                modifier = Modifier.padding(top = 10.dp),
                onClick = {
                    val categoryResponse = CategoryResponseModel(
                        name = nameState.value.text.trim(),
                        type = selectedCategoryType.value.getValue(),
                        iconId = selectedIcon.value.iconId
                    )
                    viewModel.createCategory(categoryResponse)
                }, textModifier = Modifier.padding(vertical = 17.dp)
            )
        }
        //  Show Loader
        ShowLoader(isLoading)
    }
}


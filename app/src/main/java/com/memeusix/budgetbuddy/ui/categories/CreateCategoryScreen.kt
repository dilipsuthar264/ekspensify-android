package com.memeusix.budgetbuddy.ui.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.components.AppBar
import com.memeusix.budgetbuddy.components.CustomOutlineTextField
import com.memeusix.budgetbuddy.components.FilledButton
import com.memeusix.budgetbuddy.data.ApiResponse
import com.memeusix.budgetbuddy.data.model.TextFieldStateModel
import com.memeusix.budgetbuddy.data.model.responseModel.CategoryResponseModel
import com.memeusix.budgetbuddy.data.model.responseModel.CustomIconModel
import com.memeusix.budgetbuddy.ui.categories.components.CategoryTypeSelectionToggle
import com.memeusix.budgetbuddy.ui.categories.components.IconSelectionCard
import com.memeusix.budgetbuddy.ui.categories.components.SelectAnyIconText
import com.memeusix.budgetbuddy.ui.categories.components.ShowIconLoader
import com.memeusix.budgetbuddy.ui.categories.data.CategoryType
import com.memeusix.budgetbuddy.ui.categories.viewmodel.CategoryViewModel
import com.memeusix.budgetbuddy.components.ShowLoader
import com.memeusix.budgetbuddy.utils.NavigationRequestKeys
import com.memeusix.budgetbuddy.utils.dynamicPadding
import com.memeusix.budgetbuddy.utils.handleApiResponse
import com.memeusix.budgetbuddy.utils.toastUtils.CustomToast
import com.memeusix.budgetbuddy.utils.toastUtils.CustomToastModel

@Composable
fun CreateCategoryScreen(
    navController: NavController, viewModel: CategoryViewModel
) {

    // Custom Toast
    val toastState = remember { mutableStateOf<CustomToastModel?>(null) }
    CustomToast(toastState)


    var selectedCategoryType = remember { mutableStateOf(CategoryType.INCOME) }
    val nameState = remember { mutableStateOf(TextFieldStateModel()) }
    val selectedIcon = remember { mutableStateOf(CustomIconModel()) }


    val createCategory by viewModel.createCategory.collectAsStateWithLifecycle()
    val customIconsState by viewModel.getCustomIcons.collectAsStateWithLifecycle()

    val isIconsLoading = remember { derivedStateOf { customIconsState is ApiResponse.Loading } }
    val icons = remember { derivedStateOf { customIconsState.data.orEmpty() } }

    val isLoading = remember { derivedStateOf { createCategory is ApiResponse.Loading } }


    LaunchedEffect(createCategory, customIconsState) {
        // handle Create Category Response
        handleApiResponse(
            response = createCategory,
            toastState = toastState,
            onSuccess = { data ->
                data?.let {
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        NavigationRequestKeys.CREATE_CATEGORY, true
                    )
                    navController.popBackStack()
                }
            }
        )
        // handle Custom Icon Response
        handleApiResponse(
            response = customIconsState,
            toastState = toastState,
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .dynamicPadding(paddingValues)
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
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
                    ShowIconLoader(Modifier.align(Alignment.CenterHorizontally))
                }
                if (icons.value.isNotEmpty()) {
                    IconSelectionCard(
                        icons.value,
                        selectedIcon
                    )
                }
                SelectAnyIconText(Modifier.align(Alignment.CenterHorizontally))
                Spacer(Modifier.weight(1f))
                FilledButton(
                    text = stringResource(R.string.add),
                    enabled = nameState.value.text.trim()
                        .isNotEmpty() && selectedIcon.value.id != null,
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
        }
        //  Show Loader
        ShowLoader(isLoading.value)
    }
}


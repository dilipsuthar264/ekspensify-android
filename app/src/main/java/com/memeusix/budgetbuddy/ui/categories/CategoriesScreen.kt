package com.memeusix.budgetbuddy.ui.categories

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.components.AlertDialog
import com.memeusix.budgetbuddy.components.AppBar
import com.memeusix.budgetbuddy.components.FilledButton
import com.memeusix.budgetbuddy.components.PullToRefreshLayout
import com.memeusix.budgetbuddy.components.ShowLoader
import com.memeusix.budgetbuddy.data.ApiResponse
import com.memeusix.budgetbuddy.navigation.CreateCategoryScreenRoute
import com.memeusix.budgetbuddy.ui.categories.components.CategoryList
import com.memeusix.budgetbuddy.ui.categories.viewmodel.CategoryViewModel
import com.memeusix.budgetbuddy.utils.NavigationRequestKeys
import com.memeusix.budgetbuddy.utils.dynamicImePadding
import com.memeusix.budgetbuddy.utils.getViewModelStoreOwner
import com.memeusix.budgetbuddy.utils.handleApiResponse
import com.memeusix.budgetbuddy.utils.singleClick
import com.memeusix.budgetbuddy.utils.toastUtils.CustomToast
import com.memeusix.budgetbuddy.utils.toastUtils.CustomToastModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    navController: NavHostController,
    categoryViewModel: CategoryViewModel = hiltViewModel(navController.getViewModelStoreOwner())
) {
    val getCategories by categoryViewModel.getCategories.collectAsStateWithLifecycle()
    val deleteCategories by categoryViewModel.deleteCategory.collectAsStateWithLifecycle()
    val isLoading =
        getCategories is ApiResponse.Loading || deleteCategories is ApiResponse.Loading


    //Custom Toast
    val toastState = remember { mutableStateOf<CustomToastModel?>(null) }
    CustomToast(toastState)

    // delete dialog
    val isDeleteDialogOpen = remember { mutableStateOf<Pair<Boolean, Int?>>(false to null) }

    if (isDeleteDialogOpen.value.first) {
        AlertDialog(
            title = stringResource(R.string.are_you_sure),
            message = stringResource(R.string.you_want_delete_this_category),
            btnText = stringResource(R.string.delete),
            onDismiss = singleClick {
                isDeleteDialogOpen.value =
                    isDeleteDialogOpen.value.copy(false, null)
            },
            onConfirm = singleClick {
                isDeleteDialogOpen.value.second?.let { categoryViewModel.deleteCategory(it) }
                isDeleteDialogOpen.value =
                    isDeleteDialogOpen.value.copy(false, null)
            }
        )
    }


    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    LaunchedEffect(savedStateHandle) {
        savedStateHandle?.getLiveData<Boolean>(NavigationRequestKeys.CREATE_CATEGORY)
            ?.observeForever { result ->
                if (result == true) {
                    categoryViewModel.getCategories()
                    savedStateHandle.remove<Boolean>(NavigationRequestKeys.CREATE_CATEGORY)
                }
            }
    }

    LaunchedEffect(deleteCategories) {
        handleApiResponse(response = deleteCategories,
            toastState = toastState,
            navController = navController,
            onSuccess = { data ->
                data?.let {
                    categoryViewModel.getCategories()
                    categoryViewModel.resetDeleteCategory()
                }
            })
    }

    //Main Ui
    Scaffold(
        topBar = {
            AppBar(
                heading = stringResource(R.string.categories),
                navController = navController,
                isBackNavigation = true,
            )
        },
    ) { paddingValues ->
        PullToRefreshLayout(
            onRefresh = categoryViewModel::getCategories,
            modifier = Modifier
                .fillMaxSize()
                .dynamicImePadding(paddingValues)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                CategoryList(
                    modifier = Modifier.weight(1f),
                    getCategories.data.orEmpty(),
                    onDeleteClick = {
                        isDeleteDialogOpen.value = isDeleteDialogOpen.value.copy(true, it)
                    }
                )
                FilledButton(
                    text = stringResource(R.string.add),
                    onClick = {
                        navController.navigate(
                            CreateCategoryScreenRoute(
                                categoryResponseModelArgs = null
                            )
                        )
                    },
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 20.dp),
                    textModifier = Modifier.padding(vertical = 17.dp)
                )
            }
        }
        // show Loader
        ShowLoader(isLoading)
    }
}

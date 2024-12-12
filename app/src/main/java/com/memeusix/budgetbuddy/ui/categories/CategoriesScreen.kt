package com.memeusix.budgetbuddy.ui.categories

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
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
import androidx.navigation.NavController
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.components.AppBar
import com.memeusix.budgetbuddy.components.FilledButton
import com.memeusix.budgetbuddy.components.ShowLoader
import com.memeusix.budgetbuddy.data.ApiResponse
import com.memeusix.budgetbuddy.navigation.CreateCategoryScreenRoute
import com.memeusix.budgetbuddy.ui.categories.components.CategoryList
import com.memeusix.budgetbuddy.ui.categories.viewmodel.CategoryViewModel
import com.memeusix.budgetbuddy.utils.NavigationRequestKeys
import com.memeusix.budgetbuddy.utils.dynamicPadding
import com.memeusix.budgetbuddy.utils.handleApiResponse
import com.memeusix.budgetbuddy.utils.toastUtils.CustomToast
import com.memeusix.budgetbuddy.utils.toastUtils.CustomToastModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    navController: NavController, categoryViewModel: CategoryViewModel = hiltViewModel()
) {
    val getCategories by categoryViewModel.getCategories.collectAsStateWithLifecycle()
    val deleteCategories by categoryViewModel.deleteCategory.collectAsStateWithLifecycle()
    val isLoading = remember {
        derivedStateOf {
            getCategories is ApiResponse.Loading || deleteCategories is ApiResponse.Loading
        }
    }


    //Custom Toast
    val toastState = remember { mutableStateOf<CustomToastModel?>(null) }
    CustomToast(toastState)


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

    LaunchedEffect(deleteCategories, getCategories) {

        handleApiResponse(response = deleteCategories,
            toastState = toastState,
            onSuccess = { data ->
                data?.let {
                    categoryViewModel.getCategories()
                    categoryViewModel.setToIdle()
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
        PullToRefreshBox(
            isRefreshing = isLoading.value,
            onRefresh = categoryViewModel::getCategories,
            modifier = Modifier
                .fillMaxSize()
                .dynamicPadding(paddingValues)
        ) {
            CategoryList(
                getCategories.data.orEmpty(),
                onDeleteClick = { categoryViewModel.deleteCategory(it) })
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
                    .padding(vertical = 10.dp, horizontal = 20.dp)
                    .align(Alignment.BottomCenter),
                textModifier = Modifier.padding(vertical = 17.dp)
            )
        }
        // show Loader
        ShowLoader(isLoading.value)
    }
}

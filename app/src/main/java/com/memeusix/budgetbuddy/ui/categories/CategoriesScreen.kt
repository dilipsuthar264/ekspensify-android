package com.memeusix.budgetbuddy.ui.categories

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.components.AppBar
import com.memeusix.budgetbuddy.components.CustomListItem
import com.memeusix.budgetbuddy.components.FilledButton
import com.memeusix.budgetbuddy.data.ApiResponse
import com.memeusix.budgetbuddy.navigation.CreateCategoryScreenRoute
import com.memeusix.budgetbuddy.ui.categories.data.CategoryIcons
import com.memeusix.budgetbuddy.ui.categories.data.getCategoryType
import com.memeusix.budgetbuddy.ui.categories.viewmodel.CategoryViewModel
import com.memeusix.budgetbuddy.ui.loader.ShowLoader
import com.memeusix.budgetbuddy.ui.theme.Dark10
import com.memeusix.budgetbuddy.ui.theme.Dark40
import com.memeusix.budgetbuddy.ui.theme.Red75
import com.memeusix.budgetbuddy.utils.NavigationRequestKeys
import com.memeusix.budgetbuddy.utils.toastUtils.CustomToast
import com.memeusix.budgetbuddy.utils.toastUtils.CustomToastModel
import com.memeusix.budgetbuddy.utils.toastUtils.ToastType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    navController: NavController,
    categoryViewModel: CategoryViewModel = hiltViewModel()
) {
    val getCategories by categoryViewModel.getCategories.collectAsStateWithLifecycle()
    val deleteCategories by categoryViewModel.deleteCategory.collectAsStateWithLifecycle()
    val isLoading = getCategories is ApiResponse.Loading || deleteCategories is ApiResponse.Loading

    val icons = remember { CategoryIcons.getCategoryIcons().associateBy { it.iconSlug } }

    val scrollState = rememberLazyListState()


    //Show Toast
    var toastState by remember { mutableStateOf<CustomToastModel?>(null) }
    CustomToast(toastState) {
        toastState = null
    }


    val createCategoryState =
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>(
            NavigationRequestKeys.CREATE_CATEGORY
        )

    var scroll by remember { mutableStateOf(false) }
    LaunchedEffect(createCategoryState) {
        createCategoryState?.observeForever { result ->
            if (result) {
                scroll = true
                categoryViewModel.getCategories()
            }
        }
    }
    LaunchedEffect(getCategories) {
        if (getCategories is ApiResponse.Success && scroll) {
            scrollState.animateScrollToItem(0)
            scroll = false
        }
    }



    LaunchedEffect(deleteCategories) {
        when (val response = deleteCategories) {
            is ApiResponse.Success -> {
                response.data?.let {
                    categoryViewModel.removeDeletedFromList(it)
//                    categoryViewModel.getCategories()
                }
            }

            is ApiResponse.Failure -> {
                response.errorResponse?.let {
                    toastState = CustomToastModel(
                        message = it.message,
                        type = ToastType.ERROR,
                        isVisible = true
                    )
                }
            }

            else -> Unit
        }
    }

    //Main Ui
    Scaffold(
        topBar = {
            AppBar(
                heading = stringResource(R.string.categories),
                navController = navController,
                isBackNavigation = true,
                elevation = false
            )
        },
    ) { paddingValues ->

        PullToRefreshBox(
            isRefreshing = false,
            onRefresh = categoryViewModel::getCategories
        ) {
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                LazyColumn(
                    state = scrollState,
                    modifier = Modifier
                        .weight(1f),
                ) {
                    items(
                        getCategories.data ?: emptyList(),
                        key = { it.id!! }
                    ) { category ->
                        val subtitle = category.type.getCategoryType()
                        val icon = icons[category.icon]?.icon ?: R.drawable.ic_other_category

                        CustomListItem(
                            title = category.name.orEmpty(),
                            subtitle = subtitle,
                            icon = icon,
                            modifier = Modifier.padding(vertical = 14.dp, horizontal = 20.dp),
                            enable = false,
                            leading = {
                                ActionButtons(
                                    onEditClick = {},
                                    onDeleteClick = { categoryViewModel.deleteCategory(category.id!!) },
                                    enable = category.userId != null
                                )
                            },
                            onClick = {}
                        )

                        HorizontalDivider(color = Dark10)
                    }
                }
                FilledButton(
                    text = "Add",
                    onClick = {
                        navController.navigate(
                            CreateCategoryScreenRoute(
                                categoryResponseModelArgs = null
                            )
                        )
                    },
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 20.dp),
                    textModifier = Modifier.padding(vertical = 17.dp)
                )
            }
        }


        // show Loader
        ShowLoader(isLoading)

    }
}

@Composable
private fun ActionButtons(
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    enable: Boolean = true
) {
    val editIcon = rememberAsyncImagePainter(R.drawable.ic_edit)
    val deleteIcon = rememberAsyncImagePainter(R.drawable.ic_delete)

    Row(
        modifier = Modifier
            .border(1.dp, Dark10, RoundedCornerShape(12.dp))
            .padding(vertical = 7.dp, horizontal = 10.dp)
            .alpha(if (enable) 1f else 0.2f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            painter = editIcon,
            contentDescription = "Edit",
            tint = Dark40,
            modifier = Modifier.clickable(enabled = enable, onClick = onEditClick)
        )
        VerticalDivider(
            color = Dark10,
            thickness = 1.dp,
            modifier = Modifier.height(20.dp)
        )
        Icon(
            painter = deleteIcon,
            contentDescription = "Delete",
            tint = Red75,
            modifier = Modifier.clickable(enabled = enable, onClick = onDeleteClick)
        )
    }
}

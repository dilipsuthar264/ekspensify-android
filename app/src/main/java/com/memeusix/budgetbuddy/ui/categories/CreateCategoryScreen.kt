package com.memeusix.budgetbuddy.ui.categories

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.components.AppBar
import com.memeusix.budgetbuddy.components.CustomOutlineTextField
import com.memeusix.budgetbuddy.components.FilledButton
import com.memeusix.budgetbuddy.data.ApiResponse
import com.memeusix.budgetbuddy.data.model.TextFieldStateModel
import com.memeusix.budgetbuddy.data.model.responseModel.CategoryResponseModel
import com.memeusix.budgetbuddy.navigation.CreateCategoryScreenRoute
import com.memeusix.budgetbuddy.ui.acounts.components.CustomToggleButton
import com.memeusix.budgetbuddy.ui.categories.data.CategoryIcons
import com.memeusix.budgetbuddy.ui.categories.data.CategoryType
import com.memeusix.budgetbuddy.ui.categories.viewmodel.CategoryViewModel
import com.memeusix.budgetbuddy.ui.loader.ShowLoader
import com.memeusix.budgetbuddy.ui.theme.Dark10
import com.memeusix.budgetbuddy.ui.theme.Light20
import com.memeusix.budgetbuddy.utils.NavigationRequestKeys
import com.memeusix.budgetbuddy.utils.toastUtils.CustomToast
import com.memeusix.budgetbuddy.utils.toastUtils.CustomToastModel
import com.memeusix.budgetbuddy.utils.toastUtils.ToastType

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CreateCategoryScreen(
    navController: NavController,
    args: CreateCategoryScreenRoute,
    viewModel: CategoryViewModel = hiltViewModel()
) {

    // States
    var selectedCategoryType by remember { mutableStateOf(CategoryType.INCOME) }

    val nameState = remember { mutableStateOf(TextFieldStateModel()) }

    val icons = remember { CategoryIcons.getCategoryIcons() }

    val selectedIcon = remember { mutableStateOf(icons.last()) }

    val scrollState = rememberScrollState()

    val createCategory by viewModel.createCategory.collectAsStateWithLifecycle()
    val isLoading = createCategory is ApiResponse.Loading

    // ShowToast
    var toastState by remember { mutableStateOf<CustomToastModel?>(null) }
    CustomToast(toastState) {
        toastState = null
    }

    LaunchedEffect(createCategory) {
        Log.e("", "CreateCategoryScreen: $createCategory")
        when (val response = createCategory) {
            is ApiResponse.Success -> {
                response.data?.let {
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        NavigationRequestKeys.CREATE_CATEGORY, true
                    )
                    navController.popBackStack()
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
                .padding(
                    top = paddingValues.calculateTopPadding() + 20.dp,
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 20.dp
                )
                .fillMaxSize()
                .imePadding()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CategoryTypeSelectionToggle(
                selectedCategoryType,
                onClick = { selectedCategoryType = it })

            CustomOutlineTextField(
                state = nameState,
                placeholder = stringResource(R.string.name),
                isExpendable = false,
                maxLength = 10
            )

            IconSelectionCard(
                icons,
                selectedIcon.value,
                onClick = { selectedIcon.value = it }
            )

            Text(
                stringResource(R.string.select_any_relevant_category_icon),
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 12.sp,
                    color = Light20
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.weight(1f))
            FilledButton(
                text = "Add",
                enabled = nameState.value.text.isNotEmpty(),
                onClick = {
                    val categoryResponse = CategoryResponseModel(
                        name = nameState.value.text,
                        type = selectedCategoryType.getValue(),
                        icon = selectedIcon.value.iconSlug
                    )
                    viewModel.createCategory(categoryResponse)
                },
                textModifier = Modifier.padding(vertical = 17.dp)
            )
        }

        //  Show Loader
        ShowLoader(isLoading)
    }
}

@Composable
private fun CategoryTypeSelectionToggle(
    selectedCategoryType: CategoryType,
    onClick: (CategoryType) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        CategoryType.entries.forEach { categoryType ->
            CustomToggleButton(
                text = categoryType.getDisplayName(),
                isSelected = selectedCategoryType == categoryType,
                onClick = { onClick(categoryType) },
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 12.dp),
                radius = 24.dp,
                isBorder = true,
                fontStyle = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun IconSelectionCard(
    icons: List<CategoryIcons>,
    selectedIcon: CategoryIcons,
    onClick: (CategoryIcons) -> Unit
) {
    val size = remember { mutableStateOf(IntSize.Zero) }
    val itemsPerRow =
        remember(size.value.width) { if (size.value.width == 0) 1 else (size.value.width / (50 * 3)) }
    val totalItems = icons.size
    val remainder = totalItems % itemsPerRow

    FlowRow(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .onSizeChanged { size.value = it }
            .border(
                width = 1.dp, color = Dark10, RoundedCornerShape(16.dp)
            )
            .padding(vertical = 15.dp, horizontal = 10.dp)
            .animateContentSize(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        icons.forEach {
            val selectedModifier = if (selectedIcon == it)
                Modifier.border(
                    1.dp,
                    it.iconColor,
                    RoundedCornerShape(8.dp)
                ) else Modifier

            Icon(
                painter = rememberAsyncImagePainter(it.icon),
                contentDescription = null,
                tint = it.iconColor,
                modifier = Modifier
                    .padding(5.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(it.iconColor.copy(alpha = 0.2f))
                    .size(38.dp)
                    .clickable { onClick(it) }
                    .then(selectedModifier)
                    .padding(7.dp),
            )
        }
        if (remainder != 0) {
            repeat(itemsPerRow - remainder) {
                Spacer(
                    modifier = Modifier
                        .padding(5.dp)
                        .size(38.dp),
                )
            }
        }
    }
}
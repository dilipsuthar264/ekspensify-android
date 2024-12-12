package com.memeusix.budgetbuddy.ui.categories.components


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.overscroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.components.CustomListItem
import com.memeusix.budgetbuddy.components.VerticalSpace
import com.memeusix.budgetbuddy.data.model.responseModel.CategoryResponseModel
import com.memeusix.budgetbuddy.ui.categories.data.getCategoryType
import com.memeusix.budgetbuddy.ui.theme.Dark10
import com.memeusix.budgetbuddy.ui.theme.Dark15
import com.memeusix.budgetbuddy.ui.theme.Red75


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryList(
    categoryList: List<CategoryResponseModel>,
    onDeleteClick: (Int) -> Unit
) {

    val lazyListState = rememberLazyListState()
    var previousListSize by remember { mutableIntStateOf(categoryList.size) }
    LaunchedEffect(categoryList) {
        if (categoryList.size > previousListSize) {
            lazyListState.animateScrollToItem(0)
        }
        previousListSize = categoryList.size
    }


    LazyColumn(
        state = lazyListState,
        modifier = Modifier
            .fillMaxSize(),
        userScrollEnabled = true,
        flingBehavior = ScrollableDefaults.flingBehavior(),
    ) {
        items(
            categoryList,
            key = { it.id!! },
        ) { category ->
            val subtitle = remember(category.type) { category.type.getCategoryType() }
            CustomListItem(
                title = category.name.orEmpty(),
                subtitle = subtitle,
                leadingContent = {
                    CategoryIcon(category.icon)
                },
                modifier = Modifier.padding(vertical = 14.dp, horizontal = 20.dp),
                enable = false,
                trailingContent = {
                    DeleteIconBtn(category, onDeleteClick)
                },
            )
            HorizontalDivider(color = Dark10)
        }
        item {
            VerticalSpace(76.dp)
        }
    }
}


@Composable
private fun DeleteIconBtn(
    category: CategoryResponseModel,
    onDeleteClick: (Int) -> Unit
) {
    val isEnabled = category.userId != null
    val alpha = if (isEnabled) 1f else 0.2f
    Icon(
        painter = rememberAsyncImagePainter(R.drawable.ic_delete),
        contentDescription = stringResource(R.string.delete),
        tint = Red75,
        modifier = Modifier
            .clickable(enabled = isEnabled, onClick = { onDeleteClick(category.id!!) })
            .alpha(alpha)
            .border(1.dp, Dark10, RoundedCornerShape(12.dp))
            .padding(vertical = 7.dp, horizontal = 10.dp)
    )
}
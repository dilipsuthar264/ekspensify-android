package com.memeusix.budgetbuddy.ui.categories.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
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


@Composable
fun CategoryList(
    scrollState: LazyListState,
    categoryList: List<CategoryResponseModel>,
    onDeleteClick: (Int) -> Unit
) {
    LazyColumn(
        state = scrollState,
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(
            categoryList,
            key = { it.id!! }
        ) { category ->
            val subtitle = category.type.getCategoryType()
            CustomListItem(
                title = category.name.orEmpty(),
                subtitle = subtitle,
                leadingContent = {
                    CategoryIcon(category)
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
private fun CategoryIcon(category: CategoryResponseModel) {
    AsyncImage(
        model = category.icon,
        contentDescription = null,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Dark15)
            .size(38.dp)
            .padding(7.dp)
    )
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
package com.memeusix.budgetbuddy.ui.dashboard.bottomNav.components


import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.rememberAsyncImagePainter
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.ui.theme.Blue100
import com.memeusix.budgetbuddy.ui.theme.Green100
import com.memeusix.budgetbuddy.ui.theme.Light100
import com.memeusix.budgetbuddy.ui.theme.Red100

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExpandableFab(
    isFabExpanded: Boolean
) {

    AnimatedVisibility(
        visible = isFabExpanded,
        enter = slideInVertically(initialOffsetY = { fullHeight -> fullHeight }) + fadeIn() + scaleIn(),
        exit = slideOutVertically(targetOffsetY = { fullHeight -> fullHeight }) + fadeOut() + scaleOut(),

        modifier = Modifier
            .wrapContentSize(Alignment.BottomCenter)
            .zIndex(1f)
    ) {
        val horizontalSpacing = 60.dp
        val verticalSpacing = 11.dp
        FlowRow(
            maxItemsInEachRow = 3,
            horizontalArrangement = Arrangement.spacedBy(
                horizontalSpacing,
                Alignment.CenterHorizontally
            ),
            verticalArrangement = Arrangement.spacedBy(verticalSpacing, Alignment.CenterVertically)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            ActionBtnItem(bgColor = Blue100, icon = R.drawable.ic_transfer, onClick = {})
            Spacer(modifier = Modifier.weight(1f))
            ActionBtnItem(bgColor = Green100, icon = R.drawable.ic_income, onClick = {})
            ActionBtnItem(bgColor = Red100, icon = R.drawable.ic_expense, onClick = {})
        }
    }
}

@Composable
fun ActionBtnItem(bgColor: Color, @DrawableRes icon: Int, onClick: () -> Unit) {
    val iconSize = 50.dp
    val paddingSize = 10.dp
    Box(
        modifier = Modifier
            .size(iconSize)
            .clip(CircleShape)
            .background(bgColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            rememberAsyncImagePainter(icon),
            contentDescription = null,
            tint = Light100,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingSize)
        )
    }
}

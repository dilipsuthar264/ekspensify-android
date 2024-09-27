package com.memeusix.budgetbuddy.ui.dashboard.bottomNav


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
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.ui.theme.Green100
import com.memeusix.budgetbuddy.ui.theme.Light100
import com.memeusix.budgetbuddy.ui.theme.Red100

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
            .zIndex(1f)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            ActionBtnItem(bgColor = Green100, icon = R.drawable.ic_income, onClick = {

            })
            ActionBtnItem(bgColor = Red100, icon = R.drawable.ic_expense, onClick = {})

        }
    }
}

@Composable
fun ActionBtnItem(bgColor: Color, icon: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .clip(CircleShape)
            .background(bgColor)
            .clickable(onClick = onClick)
    ) {
        Icon(
            painterResource(icon),
            contentDescription = null,
            tint = Light100,
            modifier = Modifier
                .size(60.dp)
                .padding(10.dp)
        )
    }
}
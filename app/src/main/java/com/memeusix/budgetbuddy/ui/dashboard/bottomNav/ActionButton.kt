package com.memeusix.budgetbuddy.ui.dashboard.bottomNav

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.ui.theme.Light100
import com.memeusix.budgetbuddy.ui.theme.Violet100

@Composable
fun ActionButton(isFabExpended: Boolean, modifier: Modifier, onFabClicked: () -> Unit) {
    val rotateIcon by animateFloatAsState(
        targetValue = if (!isFabExpended) 45f else 0f, label = ""
    )
    IconButton(
        onClick = onFabClicked,
        modifier = modifier.then(
            Modifier
                .size(55.dp)
                .clip(CircleShape)
                .border(BorderStroke(6.dp, Light100), CircleShape)
                .background(Violet100)
        )
    ) {
        Icon(
            painterResource(R.drawable.ic_close),
            contentDescription = null,
            tint = Light100,
            modifier = Modifier
                .wrapContentSize()
                .padding(10.dp)
                .rotate(rotateIcon)

        )

    }
}


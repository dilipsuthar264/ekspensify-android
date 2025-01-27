package com.memeusix.budgetbuddy.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomSpacer(
    height: Dp = Dp.Unspecified,
    width: Dp = Dp.Unspecified
) {
    Spacer(
        modifier = Modifier
            .let { if (height != Dp.Unspecified) it.height(height) else it }
            .let { if (width != Dp.Unspecified) it.width(width) else it }
    )
}

@Composable
fun VerticalSpace(height: Dp = Dp.Unspecified) = CustomSpacer(height = height)

@Composable
fun HorizontalSpace(width: Dp = Dp.Unspecified) = CustomSpacer(width = width)



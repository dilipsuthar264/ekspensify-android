package com.memeusix.budgetbuddy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp

@Composable
fun BottomBarWithCutout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color.White)
            .drawWithContent {
                val cutoutRadius = 36.dp.toPx()
                val shadowOffset = 10f
                val shadowBlur = 24f

                drawIntoCanvas { canvas ->
                    val paint = android.graphics
                        .Paint()
                        .apply {
                            color = android.graphics.Color.BLACK
                            setShadowLayer(
                                shadowBlur,
                                0f,
                                shadowOffset,
                                android.graphics.Color.GRAY
                            )
                            isAntiAlias = true
                        }
                    canvas.nativeCanvas.drawCircle(
                        size.width / 2,
                        0f,
                        cutoutRadius,
                        paint
                    )
                }

                // Cutout effect
                drawCircle(
                    color = Color.Transparent,
                    center = Offset(x = size.width / 2, y = 0f),
                    radius = cutoutRadius,
                    blendMode = BlendMode.DstOut
                )

                // Draw the composable content of the bottom bar
                drawContent()
            },
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

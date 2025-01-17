package com.memeusix.budgetbuddy.ui.dashboard.home.components


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.dp

@Composable
fun Chart() {
    Canvas(
        modifier = Modifier
            .size(150.dp)
            .aspectRatio(1f)
    ) {
        drawIntoCanvas {
            val width = size.width
            val radius = width / 2f
            val strokeWidth = radius * .6f
            var startAngle = 0f

            val items = listOf(254, 5002, 715, 1050, 235)

            val total = items.sum()


            items.forEach {
                val sweepAngle = (it.toFloat() / total) * 360f
                val gap = 2f
                drawArc(
                    color = Color.Gray,
                    startAngle = startAngle + gap,
                    sweepAngle = sweepAngle - gap * 2,
                    useCenter = false,
                    topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
                    size = Size(width - strokeWidth, width - strokeWidth),
                    style = Stroke(
                        strokeWidth,
                        cap = StrokeCap.Butt,
                        pathEffect = PathEffect.cornerPathEffect(10f),
                        join = StrokeJoin.Round
                    )
                )
                startAngle += sweepAngle
            }
        }
    }
}

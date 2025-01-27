package com.memeusix.budgetbuddy.ui.dashboard.transactions.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.components.CustomListItem
import com.memeusix.budgetbuddy.data.model.responseModel.AttachmentResponseModel
import com.memeusix.budgetbuddy.ui.theme.extendedColors

@Composable
fun AttachmentView(
    selectedAttachment: MutableState<AttachmentResponseModel>,
    onClick: (Boolean) -> Unit,
    onDelete: () -> Unit
) {
    val hasAttachment = selectedAttachment.value.path != null
    val borderColor = MaterialTheme.extendedColors.primaryBorder
    val borderModifier = if (hasAttachment) {
        Modifier.border(1.dp, borderColor, RoundedCornerShape(16.dp))
    } else {
        Modifier.drawBehind {
            drawRoundRect(
                color = borderColor,
                style = Stroke(
                    width = 3f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 20f), 0f)
                ),
                cornerRadius = CornerRadius(16.dp.toPx())
            )
        }
    }
    Box(
        modifier = Modifier
            .clip( RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .requiredHeight(56.dp)
            .then(borderModifier)
            .clickable { onClick(hasAttachment) }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (hasAttachment) {
            CustomListItem(
                leadingContent = {
                    AsyncImage(
                        model = selectedAttachment.value?.path,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(6.dp))
                    )
                },
                title = selectedAttachment.value?.path ?: "",
                trailingContent = {
                    Icon(
                        painterResource(R.drawable.ic_cancel_circle_half_dot),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.clickable { onDelete() }
                    )
                },
                onClick = { onClick(true) }
            )
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    8.dp,
                    Alignment.CenterHorizontally
                ),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    painterResource(R.drawable.ic_attechment),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    contentDescription = null
                )
                Text(
                    "Attachment",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }
    }
}
package com.memeusix.budgetbuddy.ui.dashboard.transactions.components

import android.net.Uri
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
import com.memeusix.budgetbuddy.ui.theme.Dark10
import com.memeusix.budgetbuddy.ui.theme.Light20

@Composable
fun AttachmentView(
    selectedAttachment: MutableState<AttachmentResponseModel>,
    onClick: (Boolean) -> Unit,
    onDelete: () -> Unit
) {
    val hasAttachment = selectedAttachment.value.path != null
    val borderModifier = if (hasAttachment) {
        Modifier.border(1.dp, Dark10, RoundedCornerShape(16.dp))
    } else {
        Modifier.drawBehind {
            drawRoundRect(
                color = Dark10,
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
            .fillMaxWidth()
            .requiredHeight(56.dp)
            .then(borderModifier)
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
                        tint = Light20,
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
                    .clickable { onClick(false) }
            ) {
                Icon(
                    painterResource(R.drawable.ic_attechment),
                    tint = Light20,
                    contentDescription = null
                )
                Text(
                    "Attachment",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 16.sp,
                        color = Light20
                    )
                )
            }
        }
    }
}
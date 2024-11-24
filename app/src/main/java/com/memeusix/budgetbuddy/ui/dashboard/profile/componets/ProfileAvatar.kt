package com.memeusix.budgetbuddy.ui.dashboard.profile.componets


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.data.model.responseModel.UserResponseModel
import com.memeusix.budgetbuddy.ui.dashboard.profile.model.AvatarOptions
import com.memeusix.budgetbuddy.ui.theme.Dark25
import com.memeusix.budgetbuddy.ui.theme.Violet20
import com.memeusix.budgetbuddy.utils.getInitials

@Composable
fun ProfileAvatar(user: UserResponseModel, onEditClick: () -> Unit) {
    val avatar =
        AvatarOptions.entries.find { it.avatarSlug == user.avatar } ?: AvatarOptions.DEFAULT
    val modifier = Modifier
        .clip(CircleShape)
        .background(color = Violet20)
        .fillMaxSize()

    Box(
        modifier = Modifier.size(100.dp), contentAlignment = Alignment.Center
    ) {
        if (avatar == AvatarOptions.DEFAULT) {
            Text(
                text = (user.name ?: "").getInitials(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 36.sp,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = modifier
                    .wrapContentHeight(Alignment.CenterVertically)
            )
        } else {
            Image(
                painter = painterResource(avatar.icon),
                contentDescription = null,
                modifier = modifier
            )
        }
        Icon(painter = painterResource(id = R.drawable.ic_edit),
            contentDescription = "Edit Icon",
            tint = Dark25,
            modifier = Modifier
                .clip(CircleShape)
                .clickable { onEditClick() }
                .border(1.dp, Violet20, CircleShape)
                .size(36.dp)
                .background(
                    color = Color.White
                )
                .padding(8.dp)
                .align(Alignment.BottomEnd))
    }
}
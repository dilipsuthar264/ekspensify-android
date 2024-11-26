package com.memeusix.budgetbuddy.ui.dashboard.profile.componets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.ui.dashboard.profile.model.ProfileOptions


@Composable
fun ProfileListItem(profileOptions: ProfileOptions, modifier: Modifier, onClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onClick() }
            .then(modifier)
    ) {
        Image(
            painter = painterResource(profileOptions.icon),
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(profileOptions.iconColor)
                .size(38.dp)
                .padding(7.dp)
        )
        Text(
            profileOptions.title, style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium
            ), modifier = Modifier.weight(1f)
        )
        Image(
            painter = painterResource(R.drawable.ic_arrow_right),
            contentDescription = null,
        )
    }
}

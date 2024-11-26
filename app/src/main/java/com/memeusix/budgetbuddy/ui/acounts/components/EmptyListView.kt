package com.memeusix.budgetbuddy.ui.acounts.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.components.FilledButton
import com.memeusix.budgetbuddy.ui.theme.interFontFamily

// Empty List View
@Composable
 fun EmptyListView(
    onClick: () -> Unit
) {
    Image(
        painter = painterResource(R.drawable.ic_no_account),
        contentDescription = null,
        modifier = Modifier.padding(horizontal = 27.dp),
        contentScale = ContentScale.Fit
    )
    Text(
        text = stringResource(R.string.no_account_message),
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        textAlign = TextAlign.Center,
        lineHeight = 22.sp,
        modifier = Modifier.padding(horizontal = 27.dp, vertical = 10.dp)
    )
    FilledButton(
        text = "Add",
        modifier = Modifier.padding(horizontal = 50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        textModifier = Modifier.padding(vertical = 17.dp),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick
    )
}

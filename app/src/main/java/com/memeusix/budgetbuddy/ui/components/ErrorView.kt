package com.memeusix.budgetbuddy.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.ui.theme.Red130
import com.memeusix.budgetbuddy.ui.theme.interFontFamily

@Composable
fun ErrorView(
    icon: Int = R.drawable.ic_oops,
    message: String,
    btnTitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            Image(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
            Text(
                message,
                fontFamily = interFontFamily,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 20.dp)
            )
            FilledButton(
                text = btnTitle,
                shape = RoundedCornerShape(16.dp),
                textModifier = Modifier.padding(vertical = 10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Red130,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                onClick = onClick
            )
        }
    }
}
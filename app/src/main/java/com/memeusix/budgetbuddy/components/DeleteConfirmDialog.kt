package com.memeusix.budgetbuddy.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.ui.theme.Dark50
import com.memeusix.budgetbuddy.ui.theme.Light40
import com.memeusix.budgetbuddy.ui.theme.Red100
import com.memeusix.budgetbuddy.ui.theme.Red20
import com.memeusix.budgetbuddy.utils.singleClick

@Composable
fun DeleteConfirmDialog(
    modifier: Modifier,
    onCancel: () -> Unit,
    onDelete: () -> Unit
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
        ) {
            Image(
                painterResource(R.drawable.ic_delete),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
            Text(
                "Are you Absolutely sure?",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                ),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(
                    10.dp,
                    Alignment.CenterHorizontally
                )
            ) {
                FilledButton(
                    text = "Cancel",
                    onClick = singleClick { onCancel() },
                    modifier = Modifier.weight(1f),
                    textModifier = Modifier.padding(vertical = 17.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Light40,
                        contentColor = Dark50
                    )
                )
                FilledButton(
                    text = "Delete",
                    onClick = singleClick { onDelete() },
                    textModifier = Modifier.padding(vertical = 17.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Red20,
                        contentColor = Red100
                    ),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }

}
package com.memeusix.budgetbuddy.ui.dashboard.transactions.filterComponets

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.memeusix.budgetbuddy.ui.theme.Dark10
import com.memeusix.budgetbuddy.ui.theme.Light20


@Composable
fun CustomFilterTextField(
    state: String,
    onChange: (String) -> Unit,
    isEnable: Boolean,
    placeHolder: String,
) {
    BasicTextField(
        value = state,
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Dark10, RoundedCornerShape(10.dp))
            .let { if (!isEnable) it.clickable(onClick = { onChange("") }) else it }
            .padding(12.dp),
        onValueChange = { if (it.length <= 10) onChange(it) },
        maxLines = 1,
        enabled = isEnable,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        textStyle = MaterialTheme.typography.bodyMedium,
        decorationBox = { innerBox ->
            if (state.isEmpty()) Text(
                placeHolder,
                color = Light20,
                style = MaterialTheme.typography.bodyMedium
            )
            innerBox()
        }
    )
}
package com.memeusix.budgetbuddy.ui.components


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.data.model.TextFieldStateModel
import com.memeusix.budgetbuddy.ui.theme.Light20
import com.memeusix.budgetbuddy.ui.theme.Red100
import com.memeusix.budgetbuddy.ui.theme.Typography

@Composable
fun CustomOutlineTextField(
    state: MutableState<TextFieldStateModel>,
    placeholder: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    maxLength: Int = Int.MAX_VALUE,
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = state.value.value,
            singleLine = true,
            onValueChange = {
                if (it.length <= maxLength) {
                    state.value = state.value.copy(value = it)
                    state.value = state.value.copy(error = null)
                }
            },
            placeholder = {
                Text(placeholder, color = Light20)
            },
            maxLines = 1,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent
            ),
            suffix = {
                if (isPassword) {
                    IconButton(onClick = {
                        isPasswordVisible = !isPasswordVisible
                    }, modifier = Modifier.size(25.dp)) {
                        Icon(
                            painterResource(if (isPasswordVisible) R.drawable.ic_eye else R.drawable.ic_eye_close),
                            null,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            },
            shape = RoundedCornerShape(16.dp),
            visualTransformation = if (isPassword && !isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = if (state.value.isValid()) Light20 else Red100,
                    RoundedCornerShape(16.dp)
                )
                .fillMaxWidth()
                .then(modifier)
        )
        state.value.error?.let {
            Text(
                text = it,
                color = Red100,
                style = Typography.labelSmall,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 2.dp)
            )
        }

    }
}

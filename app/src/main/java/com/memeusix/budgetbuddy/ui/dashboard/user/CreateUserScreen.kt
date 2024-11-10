package com.memeusix.budgetbuddy.ui.dashboard.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.memeusix.budgetbuddy.data.model.TextFieldStateModel
import com.memeusix.budgetbuddy.ui.components.AppBar
import com.memeusix.budgetbuddy.ui.components.CustomOutlineTextField
import com.memeusix.budgetbuddy.ui.theme.Light100
import com.memeusix.budgetbuddy.ui.theme.Typography

@Composable
fun CreateUserScreen(
    navController: NavController,
) {
    val nameState = remember { mutableStateOf(TextFieldStateModel()) }
    val phoneNumber = remember { mutableStateOf(TextFieldStateModel()) }
    val about = remember { mutableStateOf(TextFieldStateModel()) }
    Scaffold(
        topBar = {
            AppBar(
                heading = "Add New User",
                navController = navController,
                elevation = true,
            )
        }, containerColor = Light100
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    vertical = paddingValues.calculateTopPadding() + 20.dp, horizontal = 20.dp
                ),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            TextFieldTitle(
                title = "User Name"
            )
            CommonTextField(
                state = nameState,
                maxLength = 25,
                placeHolder = "Enter user name",
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            )
            TextFieldTitle("Phone Number (Optional)")
            CommonTextField(
                state = phoneNumber,
                maxLength = 10,
                placeHolder = "Enter user phone number",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                )
            )
            TextFieldTitle("About (Optional)")
            CommonTextField(
                state = about,
                maxLength = 10,
                isExpendable = true,
                placeHolder = "Enter about user",
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            )
            Spacer(Modifier.height(30.dp))
        }
    }
}

@Composable
private fun TextFieldTitle(title: String) {
    Text(
        title,
        style = Typography.bodySmall,
        modifier = Modifier.padding(top = 8.dp, start = 2.dp),
    )
}

@Composable
fun CommonTextField(
    state: MutableState<TextFieldStateModel>,
    maxLength: Int,
    placeHolder: String,
    isExpendable: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    CustomOutlineTextField(
        state = state,
        maxLength = maxLength,
        radius = 10.dp,
        placeholder = placeHolder,
        isExpendable = isExpendable,
        color = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            errorBorderColor = Color.Transparent,
            unfocusedContainerColor = Light100,
            focusedContainerColor = Light100,
            errorContainerColor = Light100
        ),
        keyboardOptions = keyboardOptions
    )
}

package com.memeusix.budgetbuddy.ui.auth

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.data.model.TextFieldStateModel
import com.memeusix.budgetbuddy.navigation.LoginScreenRoute
import com.memeusix.budgetbuddy.navigation.OtpVerificationScreenRoute
import com.memeusix.budgetbuddy.navigation.RegisterScreenRoute
import com.memeusix.budgetbuddy.ui.auth.viewModel.AuthViewModel
import com.memeusix.budgetbuddy.ui.components.AppBar
import com.memeusix.budgetbuddy.ui.components.CustomOutlineTextField
import com.memeusix.budgetbuddy.ui.components.FilledButton
import com.memeusix.budgetbuddy.ui.theme.Dark100
import com.memeusix.budgetbuddy.ui.theme.Typography
import com.memeusix.budgetbuddy.ui.theme.Violet100
import com.memeusix.budgetbuddy.utils.isValidEmail
import com.memeusix.budgetbuddy.utils.isValidPassword

@Composable
fun RegisterScreen(
    navController: NavHostController, authViewModel: AuthViewModel = hiltViewModel()
) {
    val nameState = remember { mutableStateOf(TextFieldStateModel()) }
    val emailState = remember { mutableStateOf(TextFieldStateModel()) }
    val passwordState = remember { mutableStateOf(TextFieldStateModel()) }
    var isCheckBoxCheck by remember { mutableStateOf(false) }

    val registerState by authViewModel.register.collectAsState()

    val context = LocalContext.current

    Scaffold(
        topBar = {
            AppBar(stringResource(R.string.sign_up), navController, false)
        },
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(
                    horizontal = 30.dp, vertical = paddingValues.calculateTopPadding() + 56.dp
                )
                .fillMaxSize(),
        ) {
            CustomOutlineTextField(
                state = nameState,
                placeholder = "Name",
            )
            Spacer(Modifier.height(24.dp))
            CustomOutlineTextField(
                state = emailState,
                placeholder = "Email",
            )
            Spacer(Modifier.height(24.dp))
            CustomOutlineTextField(
                state = passwordState, placeholder = "Password", isPassword = true, maxLength = 20
            )
            Spacer(Modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Checkbox(
                    checked = isCheckBoxCheck, onCheckedChange = {
                        isCheckBoxCheck = it
                    }, modifier = Modifier.size(30.dp), colors = CheckboxDefaults.colors(
                        uncheckedColor = Violet100
                    )
                )
                Text(
                    stringResource(R.string.by_signing_up_you_agree_to_the_terms_of_service_and_privacy_policy),
                    style = Typography.bodySmall
                )
            }
            Spacer(Modifier.height(30.dp))
            if (registerState.isLoading()) {
                CircularProgressIndicator()
            } else {
                FilledButton(text = stringResource(R.string.sign_up),
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        navController.navigate(
                            OtpVerificationScreenRoute(
                                name = nameState.value.value,
                                email = emailState.value.value,
                                password = passwordState.value.value
                            )
                        )
//                        if (
//                            validateAndSubmit(
//                                nameState,
//                                emailState,
//                                passwordState,
//                                isCheckBoxCheck,
//                                context
//                            )
//                        ) {
//                            authViewModel.register(
//                                AuthRequestModel(
//                                    name = nameState.value.value,
//                                    email = emailState.value.value,
//                                    password = passwordState.value.value
//                                )
//                            )
//                        }
                    })
            }
            Spacer(Modifier.height(20.dp))
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.clickable {
                navController.navigate(LoginScreenRoute) {
                    popUpTo(RegisterScreenRoute) { inclusive = true }
                }
            }) {
                Text(
                    stringResource(R.string.already_have_an_account),
                    color = Dark100,
                    style = Typography.bodyMedium,
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    stringResource(R.string.login),
                    color = Violet100,
                    style = Typography.bodyMedium,
                )
            }
        }
    }
}

// validation
fun validateAndSubmit(
    nameState: MutableState<TextFieldStateModel>,
    emailState: MutableState<TextFieldStateModel>,
    passwordState: MutableState<TextFieldStateModel>,
    isCheckBoxCheck: Boolean,
    context: Context,
): Boolean {
    return when {
        nameState.value.value.isEmpty() -> {
            nameState.value =
                nameState.value.copy(error = context.getString(R.string.please_enter_your_name))
            false
        }

        emailState.value.value.isEmpty() -> {
            emailState.value =
                emailState.value.copy(error = context.getString(R.string.please_enter_your_email))
            false
        }

        !emailState.value.value.isValidEmail() -> {
            emailState.value =
                emailState.value.copy(error = context.getString(R.string.please_enter_a_valid_email))
            false
        }

        passwordState.value.value.isEmpty() -> {
            passwordState.value =
                passwordState.value.copy(error = context.getString(R.string.please_enter_your_password))
            false
        }

        !passwordState.value.value.isValidPassword() -> {
            passwordState.value =
                passwordState.value.copy(error = context.getString(R.string.password_must_be_at_least_8_characters))
            false
        }

        !isCheckBoxCheck -> {
            Toast.makeText(
                context,
                context.getString(R.string.please_accept_terms_and_conditions),
                Toast.LENGTH_SHORT
            ).show()
            false
        }

        else -> {
            true
        }
    }
}
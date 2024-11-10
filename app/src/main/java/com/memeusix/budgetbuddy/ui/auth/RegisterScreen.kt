package com.memeusix.budgetbuddy.ui.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.data.ApiResponse
import com.memeusix.budgetbuddy.data.model.TextFieldStateModel
import com.memeusix.budgetbuddy.data.model.requestModel.AuthRequestModel
import com.memeusix.budgetbuddy.navigation.BottomNavRoute
import com.memeusix.budgetbuddy.navigation.LoginScreenRoute
import com.memeusix.budgetbuddy.navigation.OtpVerificationScreenRoute
import com.memeusix.budgetbuddy.navigation.RegisterScreenRoute
import com.memeusix.budgetbuddy.ui.auth.components.DontHaveAccountText
import com.memeusix.budgetbuddy.ui.auth.components.GoogleAuthBtn
import com.memeusix.budgetbuddy.ui.auth.viewModel.AuthViewModel
import com.memeusix.budgetbuddy.ui.components.AppBar
import com.memeusix.budgetbuddy.ui.components.CustomOutlineTextField
import com.memeusix.budgetbuddy.ui.components.FilledButton
import com.memeusix.budgetbuddy.ui.loader.ShowLoader
import com.memeusix.budgetbuddy.ui.theme.Typography
import com.memeusix.budgetbuddy.utils.isValidEmail

@Composable
fun RegisterScreen(
    navController: NavHostController, authViewModel: AuthViewModel = hiltViewModel()
) {
    val nameState = remember { mutableStateOf(TextFieldStateModel()) }
    val emailState = remember { mutableStateOf(TextFieldStateModel()) }
    var isCheckBoxCheck by remember { mutableStateOf(false) }

    // register api response state
    val registerResponse by authViewModel.register.collectAsState()

    // register with google state
    val signUpWithGoogleResponse by authViewModel.signUpWithGoogle.collectAsState()

    // getting context
    val context = LocalContext.current

    // coroutines for thread handling in oAuth
    val coroutines = rememberCoroutineScope()


    // loading state
    val isLoading =
        registerResponse is ApiResponse.Loading || signUpWithGoogleResponse is ApiResponse.Loading


    // scroll page state
    val scrollState = rememberScrollState()


    LaunchedEffect(registerResponse) {
        when (registerResponse) {
            is ApiResponse.Success -> {
                navController.navigate(
                    OtpVerificationScreenRoute(
                        name = nameState.value.text.trim(),
                        email = emailState.value.text.trim(),
                    )
                )
            }

            is ApiResponse.Failure -> {
                Toast.makeText(
                    context,
                    registerResponse.errorResponse?.message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> {}
        }
    }
    LaunchedEffect(signUpWithGoogleResponse) {
        when (signUpWithGoogleResponse) {
            is ApiResponse.Success -> {
                navController.navigate(
                    BottomNavRoute
                )
            }

            is ApiResponse.Failure -> {
                Toast.makeText(
                    context,
                    signUpWithGoogleResponse.errorResponse?.message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> {}
        }
    }


    // Shows the loader
    ShowLoader(isLoading)


    // Main Ui
    Scaffold(
        topBar = {
            AppBar(stringResource(R.string.sign_up), navController, false)
        },
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .imePadding()
                .padding(
                    start = 16.dp, end = 16.dp, top = paddingValues.calculateTopPadding() + 56.dp
                )
                .fillMaxSize()
                .verticalScroll(scrollState),
        ) {
            CustomOutlineTextField(
                state = nameState,
                placeholder = "Name",
                isExpendable = false,
            )
            Spacer(Modifier.height(24.dp))
            CustomOutlineTextField(
                state = emailState,
                placeholder = "Email",
                isExpendable = false,
            )
            Spacer(Modifier.height(16.dp))
            TermsAndConditionCheckBox(isCheckBoxCheck) {
                isCheckBoxCheck = it
            }
            Spacer(Modifier.height(30.dp))
            FilledButton(text = stringResource(R.string.send_otp),
                shape = RoundedCornerShape(16.dp),
                textModifier = Modifier.padding(vertical = 8.dp),
                onClick = {
                    when {
                        nameState.value.text.trim().isEmpty() -> {
                            nameState.value = nameState.value.copy(error = "please enter your name")
                        }

                        emailState.value.text.trim().isEmpty() -> {
                            emailState.value =
                                emailState.value.copy(error = context.getString(R.string.please_enter_your_email))
                        }

                        !emailState.value.text.trim().isValidEmail() -> {
                            emailState.value =
                                emailState.value.copy(error = context.getString(R.string.please_enter_a_valid_email))
                        }

                        !isCheckBoxCheck -> {
                            Toast.makeText(
                                context,
                                "Please check terms and conditions",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        else -> {
                            authViewModel.register(
                                AuthRequestModel(
                                    name = nameState.value.text.trim(),
                                    email = emailState.value.text.trim()
                                )
                            )
                        }
                    }
                })
            Spacer(Modifier.height(15.dp))
            Text(
                text = "Or",
                style = Typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(15.dp))
            GoogleAuthBtn(
                onClick = {
                    authViewModel.launchGoogleAuth(context, coroutines) { tokenId ->
                        authViewModel.signUpWithGoogle(tokenId)
                    }
                }
            )

            Spacer(Modifier.height(33.dp))
            DontHaveAccountText(
                firstText = stringResource(R.string.already_have_an_account),
                secondText = stringResource(R.string.login)
            ) {
                navController.navigate(LoginScreenRoute) {
                    popUpTo(RegisterScreenRoute) { inclusive = true }
                }
            }
        }

    }
}

@Composable
private fun TermsAndConditionCheckBox(isCheckBoxCheck: Boolean, onCheckChange: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Checkbox(
            checked = isCheckBoxCheck, onCheckedChange = {
                onCheckChange(it)
            }, modifier = Modifier.size(30.dp), colors = CheckboxDefaults.colors(
                uncheckedColor = MaterialTheme.colorScheme.primary
            )
        )
        Text(
            stringResource(R.string.by_signing_up_you_agree_to_the_terms_of_service_and_privacy_policy),
            style = MaterialTheme.typography.bodySmall
        )
    }
}
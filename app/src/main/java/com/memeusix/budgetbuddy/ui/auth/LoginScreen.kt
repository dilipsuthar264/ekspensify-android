package com.memeusix.budgetbuddy.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.data.ApiResponse
import com.memeusix.budgetbuddy.data.model.TextFieldStateModel
import com.memeusix.budgetbuddy.data.model.requestModel.AuthRequestModel
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
import com.memeusix.budgetbuddy.utils.SpUtils
import com.memeusix.budgetbuddy.utils.goToNextScreenAfterLogin
import com.memeusix.budgetbuddy.utils.isValidEmail
import com.memeusix.budgetbuddy.utils.toastUtils.CustomToast
import com.memeusix.budgetbuddy.utils.toastUtils.CustomToastModel
import com.memeusix.budgetbuddy.utils.toastUtils.ToastType

@Composable
fun LoginScreen(
    navController: NavController,
    spUtils: SpUtils,
    authViewModel: AuthViewModel = hiltViewModel()
) {

    // For toast state
    var toastState by remember { mutableStateOf<CustomToastModel?>(null) }
    CustomToast(toastState) {
        toastState = null
    }

    // email and password states
    val email: MutableState<TextFieldStateModel> =
        remember { mutableStateOf(TextFieldStateModel()) }

    val coroutines = rememberCoroutineScope()

    // getting context
    val context = LocalContext.current

    // login state for api
    val sendOtpResponse by authViewModel.sendOtp.collectAsState()
    val loginWithGoogleResponse by authViewModel.signInWithGoogle.collectAsState()


    val isLoading =
        sendOtpResponse is ApiResponse.Loading || loginWithGoogleResponse is ApiResponse.Loading


    val scrollState = rememberScrollState()

    LaunchedEffect(sendOtpResponse) {
        when (sendOtpResponse) {
            is ApiResponse.Success -> {
                navController.navigate(
                    OtpVerificationScreenRoute(
                        email = email.value.text, name = null
                    )
                )
            }

            is ApiResponse.Failure -> {
                sendOtpResponse.errorResponse?.apply {
                    if (!this.details.isNullOrEmpty()) {
                        email.value = email.value.copy(error = this.message)
                    } else {
                        toastState = CustomToastModel(
                            message = this.message,
                            isVisible = true,
                            type = ToastType.ERROR
                        )
                    }
                }
            }

            else -> {}
        }
    }

    LaunchedEffect(loginWithGoogleResponse) {
        when (loginWithGoogleResponse) {
            is ApiResponse.Success -> {
                loginWithGoogleResponse.data?.apply {
                    if (user != null && !token.isNullOrEmpty()) {
                        spUtils.user = user
                        spUtils.accessToken = token!!
                        spUtils.isLoggedIn = true

                        goToNextScreenAfterLogin(navController)
                    }

                }
            }

            is ApiResponse.Failure -> {
                loginWithGoogleResponse.errorResponse?.apply {
                    toastState = CustomToastModel(
                        message = this.message,
                        isVisible = true,
                        type = ToastType.ERROR
                    )
                }
            }

            else -> {}
        }
    }

    // Shows The Loader for Api Requests

    ShowLoader(isLoading)

    // Main Ui For Login Screen

    Scaffold(
        topBar = {
            AppBar(stringResource(R.string.login), navController, false)
        },
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .imePadding()
                .padding(
                    start = 20.dp, end = 20.dp, top = paddingValues.calculateTopPadding()
                )
                .fillMaxSize()
                .verticalScroll(scrollState),
        ) {
            LoginImage()
            CustomOutlineTextField(
                state = email,
                placeholder = stringResource(R.string.email),
                modifier = Modifier,
                isExpendable = false,
                maxLength = 40
            )
            Spacer(Modifier.height(24.dp))
            FilledButton(text = stringResource(R.string.send_otp),
                shape = RoundedCornerShape(16.dp),
                textModifier = Modifier.padding(vertical = 17.dp),
                onClick = {
                    when {
                        email.value.text.trim().isEmpty() -> {
                            email.value =
                                email.value.copy(error = context.getString(R.string.please_enter_your_email))
                        }

                        !email.value.text.trim().isValidEmail() -> {
                            email.value =
                                email.value.copy(error = context.getString(R.string.please_enter_a_valid_email))
                        }

                        else -> {
                            authViewModel.sendOtp(
                                AuthRequestModel(
                                    email = email.value.text.trim()
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
                text = stringResource(R.string.login_with_google),
                onClick = {
                    authViewModel.launchGoogleAuth(context, coroutines) { tokenId ->
                        authViewModel.signInWithGoogle(tokenId)
                    }
                }
            )
            Spacer(Modifier.height(33.dp))
            DontHaveAccountText(
                firstText = stringResource(R.string.don_t_have_an_account),
                secondText = stringResource(R.string.sign_up)
            ) {
                navController.navigate(RegisterScreenRoute) {
                    popUpTo(LoginScreenRoute) { inclusive = true }
                }
            }
        }

    }

}


@Composable
private fun LoginImage() {
    Image(
        painter = painterResource(R.drawable.ic_login_bg),
        contentDescription = null,
        contentScale = ContentScale.Fit,
    )
}



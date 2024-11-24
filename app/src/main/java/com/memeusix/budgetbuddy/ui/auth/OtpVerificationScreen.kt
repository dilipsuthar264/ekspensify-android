package com.memeusix.budgetbuddy.ui.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.data.ApiResponse
import com.memeusix.budgetbuddy.data.model.requestModel.AuthRequestModel
import com.memeusix.budgetbuddy.ui.auth.viewModel.AuthViewModel
import com.memeusix.budgetbuddy.ui.components.AppBar
import com.memeusix.budgetbuddy.ui.components.FilledButton
import com.memeusix.budgetbuddy.ui.components.OtpInputField
import com.memeusix.budgetbuddy.ui.loader.ShowLoader
import com.memeusix.budgetbuddy.ui.theme.Typography
import com.memeusix.budgetbuddy.utils.SpUtils
import com.memeusix.budgetbuddy.utils.goToNextScreenAfterLogin
import com.memeusix.budgetbuddy.utils.toastUtils.CustomToast
import com.memeusix.budgetbuddy.utils.toastUtils.CustomToastModel
import com.memeusix.budgetbuddy.utils.toastUtils.ToastType
import kotlinx.coroutines.delay

@Composable
fun OtpVerificationScreen(
    navController: NavController,
    navArgs: AuthRequestModel,
    spUtils: SpUtils,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    // getting Context
    val context = LocalContext.current

    // otp state
    var otpValue by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    var timeRemaining by remember { mutableIntStateOf(30) }
    var isResendEnable by remember { mutableStateOf(false) }

    // Login state for api
    val loginResponse by authViewModel.login.collectAsState()

    val sendOtpResponse by authViewModel.sendOtp.collectAsState()

    val isLoading = loginResponse is ApiResponse.Loading || sendOtpResponse is ApiResponse.Loading


    /**
     * Setting up Toast
     */
    var toastState by remember { mutableStateOf<CustomToastModel?>(null) }
    CustomToast(toastState) {
        toastState = null
    }


    LaunchedEffect(key1 = timeRemaining) {
        if (timeRemaining > 0) {
            delay(1000)
            timeRemaining--
        } else {
            isResendEnable = true
        }
    }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    val scrollState = rememberScrollState()

    LaunchedEffect(loginResponse) {
        when (loginResponse) {
            is ApiResponse.Success -> {
                loginResponse.data?.apply {
                    if (user != null && !token.isNullOrEmpty()) {
                        spUtils.user = user
                        spUtils.accessToken = token!!
                        spUtils.isLoggedIn = true

                        goToNextScreenAfterLogin(navController)
                    }

                }
            }

            is ApiResponse.Failure -> {
                loginResponse.errorResponse?.apply {
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

    LaunchedEffect(sendOtpResponse) {
        when (sendOtpResponse) {
            is ApiResponse.Success -> {
                // Show Success Message
                timeRemaining = 30
                isResendEnable = false

                toastState = CustomToastModel(
                    message = context.getString(R.string.otp_sent_successfully),
                    isVisible = true,
                    type = ToastType.SUCCESS
                )

            }

            is ApiResponse.Failure -> {
                sendOtpResponse.errorResponse?.apply {
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

    // Main Ui For Otp Verification Screen

    Scaffold(
        topBar = {
            AppBar(stringResource(R.string.verification), navController, false)
        },
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(30.dp),
            modifier = Modifier
                .imePadding()
                .padding(
                    start = 20.dp, end = 20.dp, top = paddingValues.calculateTopPadding() + 20.dp
                )
                .fillMaxSize()
                .verticalScroll(scrollState),
        ) {
            Text(
                stringResource(R.string.enter_your_verification_code),
                style = Typography.titleLarge,
            )
            OtpInputField(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fillMaxWidth(0.7f),
                otpText = otpValue,
                shouldCursorBlink = true,
                onOTPChanged = { value ->
                    otpValue = value
                },
                shouldShowCursor = true,
            )
            Text(
                text = if (isResendEnable) stringResource(R.string.resend_otp) else stringResource(
                    R.string._00,
                    timeRemaining
                ),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .clickable(enabled = isResendEnable) {
                        authViewModel.sendOtp(
                            AuthRequestModel(email = navArgs.email)
                        )
                    }
            )
            Text(
                buildAnnotatedString {
                    append(stringResource(R.string.we_send_verification_code_to_your_email))
                    withStyle(
                        style = SpanStyle(color = MaterialTheme.colorScheme.primary)
                    ) {
                        append(navArgs.email)
                    }
                    append(stringResource(R.string.you_can_check_your_inbox))
                },
                style = Typography.bodyLarge,
                modifier = Modifier.padding(end = 20.dp)
            )
            FilledButton(
                text = stringResource(R.string.verify),
                shape = RoundedCornerShape(16.dp),
                textModifier = Modifier.padding(vertical = 17.dp),
                onClick = {
                    if (otpValue.isNotEmpty() && otpValue.length == 6) {
                        authViewModel.login(
                            AuthRequestModel(
                                email = navArgs.email,
                                otp = otpValue.trim()
                            )
                        )
                    }
                },
                enabled = otpValue.length == 6
            )
        }
    }
}


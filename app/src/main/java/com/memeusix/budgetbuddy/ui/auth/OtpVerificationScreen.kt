package com.memeusix.budgetbuddy.ui.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.navigation.NavHostController
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.components.AppBar
import com.memeusix.budgetbuddy.components.FilledButton
import com.memeusix.budgetbuddy.components.OtpInputField
import com.memeusix.budgetbuddy.components.ShowLoader
import com.memeusix.budgetbuddy.data.ApiResponse
import com.memeusix.budgetbuddy.data.model.requestModel.AuthRequestModel
import com.memeusix.budgetbuddy.ui.auth.viewModel.AuthViewModel
import com.memeusix.budgetbuddy.ui.theme.Typography
import com.memeusix.budgetbuddy.utils.dynamicImePadding
import com.memeusix.budgetbuddy.utils.goToNextScreenAfterLogin
import com.memeusix.budgetbuddy.utils.handleApiResponse
import com.memeusix.budgetbuddy.utils.toastUtils.CustomToast
import com.memeusix.budgetbuddy.utils.toastUtils.CustomToastModel
import com.memeusix.budgetbuddy.utils.toastUtils.ToastType
import com.onesignal.OneSignal
import kotlinx.coroutines.delay

@Composable
fun OtpVerificationScreen(
    navController: NavHostController,
    navArgs: AuthRequestModel,
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

    // Custom Toast
    val toastState = remember { mutableStateOf<CustomToastModel?>(null) }
    CustomToast(toastState)


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
    LaunchedEffect(loginResponse, sendOtpResponse) {
        handleApiResponse(
            response = loginResponse,
            toastState = toastState,
            navController = navController,
            onSuccess = { data ->
                data?.apply {
                    if (user != null && !token.isNullOrEmpty()) {
                        authViewModel.spUtilsManager.logout()
                        authViewModel.spUtilsManager.updateUser(user)
                        authViewModel.spUtilsManager.updateAccessToken(token!!)
                        authViewModel.spUtilsManager.updateLoginStatus(true)
                        OneSignal.login(user!!.id.toString())
                        goToNextScreenAfterLogin(navController)
                    }
                }
            }
        )
        handleApiResponse(
            response = sendOtpResponse,
            toastState = toastState,
            navController = navController,
            onSuccess = { data ->
                timeRemaining = 30
                isResendEnable = false
                toastState.value = CustomToastModel(
                    message = context.getString(R.string.otp_sent_successfully),
                    isVisible = true,
                    type = ToastType.SUCCESS
                )
            }
        )
    }


    // Main Ui
    Scaffold(
        topBar = {
            AppBar(stringResource(R.string.verification), navController)
        },
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(30.dp),
            modifier = Modifier
                .fillMaxSize()
                .dynamicImePadding(paddingValues)
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Text(
                stringResource(R.string.enter_your_verification_code),
                style = MaterialTheme.typography.titleLarge,
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
                color = MaterialTheme.colorScheme.primaryContainer,
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
                        style = SpanStyle(color = MaterialTheme.colorScheme.primaryContainer)
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


        ShowLoader(isLoading)
    }
}


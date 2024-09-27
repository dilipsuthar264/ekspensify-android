package com.memeusix.budgetbuddy.ui.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.memeusix.budgetbuddy.data.model.AuthRequestModel
import com.memeusix.budgetbuddy.ui.auth.viewModel.AuthViewModel
import com.memeusix.budgetbuddy.ui.components.AppBar
import com.memeusix.budgetbuddy.ui.components.FilledButton
import com.memeusix.budgetbuddy.ui.components.OtpInputField
import com.memeusix.budgetbuddy.ui.theme.Typography
import com.memeusix.budgetbuddy.ui.theme.Violet100
import kotlinx.coroutines.delay

@Composable
fun OtpVerificationScreen(
    navController: NavController,
    navArgs: AuthRequestModel,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var otpValue by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    var timeRemaining by remember { mutableIntStateOf(30) }
    var isResendEnable by remember { mutableStateOf(false) }


    LaunchedEffect(key1 = timeRemaining) {
        if (timeRemaining >0){
            delay(1000)
            timeRemaining--
        }else{
            isResendEnable = true
        }
    }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Scaffold(
        topBar = {
            AppBar(stringResource(R.string.verification), navController, false)
        },
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(30.dp),
            modifier = Modifier
                .padding(
                    horizontal = 20.dp, vertical = paddingValues.calculateTopPadding() + 20.dp
                )
                .fillMaxSize(),
        ) {
            Text(
                stringResource(R.string.enter_your_verification_code),
                style = Typography.titleLarge
            )
            OtpInputField(
                modifier = Modifier
                    .focusRequester(focusRequester),
                otpText = otpValue,
                shouldCursorBlink = false,
                onOTPChanged = { value ->
                    otpValue = value
                }
            )
            Text(
                text = if (isResendEnable) stringResource(R.string.resend_otp) else stringResource(
                    R.string._00,
                    timeRemaining
                ),
                style = Typography.bodyLarge,
                color = Violet100,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .clickable(enabled = isResendEnable) {
                        timeRemaining = 30
                        isResendEnable = false
                    }
            )
            Text(
                buildAnnotatedString {
                    append(stringResource(R.string.we_send_verification_code_to_your_email))
                    withStyle(
                        style = SpanStyle(color = Violet100)
                    ) {
                        append(navArgs.email)
                    }
                    append(stringResource(R.string.you_can_check_your_inbox))
                },
                style = Typography.bodyLarge,
                modifier = Modifier.padding(end = 20.dp)
            )
            FilledButton(
                text = "Verify",
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
                onClick = {

                }
            )
        }
    }
}


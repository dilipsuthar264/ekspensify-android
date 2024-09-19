package com.memeusix.budgetbuddy.ui.auth

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.data.model.AuthRequestModel
import com.memeusix.budgetbuddy.data.model.TextFieldStateModel
import com.memeusix.budgetbuddy.navigation.RouteNames
import com.memeusix.budgetbuddy.ui.auth.viewModel.AuthViewModel
import com.memeusix.budgetbuddy.ui.components.AppBar
import com.memeusix.budgetbuddy.ui.components.CustomOutlineTextField
import com.memeusix.budgetbuddy.ui.components.FilledButton
import com.memeusix.budgetbuddy.ui.theme.Dark100
import com.memeusix.budgetbuddy.ui.theme.Dark50
import com.memeusix.budgetbuddy.ui.theme.Light40
import com.memeusix.budgetbuddy.ui.theme.Typography
import com.memeusix.budgetbuddy.ui.theme.Violet100
import com.memeusix.budgetbuddy.utils.isValidEmail
import com.memeusix.budgetbuddy.utils.isValidPassword

@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel = hiltViewModel()) {

    // email and password states

    val email: MutableState<TextFieldStateModel> =
        remember { mutableStateOf(TextFieldStateModel()) }
    val password: MutableState<TextFieldStateModel> =
        remember { mutableStateOf(TextFieldStateModel()) }

    // getting context
    val context = LocalContext.current

    // login state for api
    val loginState by authViewModel.login.collectAsState()

    // google sign in intent Launcher
    val signInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { intent ->
                authViewModel.handleGoogleSignInResult(intent)
            }
        }
    }



    Scaffold(
        topBar = {
            AppBar(stringResource(R.string.login), navController, false)
        },
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(
                    horizontal = 30.dp, vertical = paddingValues.calculateTopPadding()
                )
                .padding(top = 56.dp)
                .fillMaxSize(),
        ) {
            CustomOutlineTextField(
                state = email,
                placeholder = "Email",
                modifier = Modifier
            )
            Spacer(Modifier.height(24.dp))
            CustomOutlineTextField(
                state = password,
                placeholder = "Password",
                modifier = Modifier,
                isPassword = true
            )
            Spacer(Modifier.height(40.dp))
            if (loginState.isLoading()) {
                CircularProgressIndicator()
            } else {
                FilledButton(
                    text = "Login",
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        when {
                            email.value.value.isEmpty() -> {
                                email.value =
                                    email.value.copy(error = context.getString(R.string.please_enter_your_email))
                            }

                            !email.value.value.isValidEmail() -> {
                                email.value =
                                    email.value.copy(error = context.getString(R.string.please_enter_a_valid_email))
                            }

                            password.value.value.isEmpty() -> {
                                password.value =
                                    password.value.copy(error = context.getString(R.string.please_enter_your_password))
                            }

                            !password.value.value.isValidPassword() -> {
                                password.value =
                                    password.value.copy(error = context.getString(R.string.password_must_be_at_least_8_characters))
                            }

                            else -> {
                                authViewModel.login(
                                    AuthRequestModel(
                                        email = email.value.value,
                                        password = password.value.value
                                    )
                                )
                            }
                        }
                    }
                )
            }
            Spacer(Modifier.height(33.dp))
            Text(
                stringResource(R.string.forgot_password),
                color = Violet100,
                style = Typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.clickable {

                }
            )
            Spacer(Modifier.height(33.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = Light40, RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .clickable {
                        authViewModel.signInWithGoogle(context) { intent ->
                            signInLauncher.launch(intent)
                        }
                    }
            ) {
                Image(
                    painterResource(R.drawable.ic_google),
                    null,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .size(28.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    stringResource(R.string.login_with_google),
                    style = Typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = Dark50
                )
            }
            Spacer(Modifier.height(33.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .clickable {
                        navController.navigate(RouteNames.REGISTER_SCREEN){
                            popUpTo(RouteNames.LOGIN_SCREEN) { inclusive = true }
                        }
                    }
            ) {
                Text(
                    stringResource(R.string.don_t_have_an_account),
                    color = Dark100,
                    style = Typography.bodyMedium,
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    stringResource(R.string.sign_up),
                    color = Violet100,
                    style = Typography.bodyMedium,
                )
            }

        }
    }
}



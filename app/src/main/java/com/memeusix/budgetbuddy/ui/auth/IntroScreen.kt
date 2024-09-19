package com.memeusix.budgetbuddy.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.navigation.RouteNames
import com.memeusix.budgetbuddy.ui.components.FilledButton
import com.memeusix.budgetbuddy.ui.theme.Dark25
import com.memeusix.budgetbuddy.ui.theme.Dark50
import com.memeusix.budgetbuddy.ui.theme.Typography
import com.memeusix.budgetbuddy.ui.theme.Violet100
import com.memeusix.budgetbuddy.ui.theme.Violet20

@Composable
fun IntroScreen(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp)
    ) {
        Image(painter = painterResource(R.drawable.ic_hand_money), null)
        Spacer(modifier = Modifier.height(20.dp))
        Text(stringResource(R.string.app_name), style = Typography.titleLarge, color = Dark50)

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            stringResource(R.string.become_your_own_money_manager_and_make_every_rupee_count),
            style = Typography.bodyLarge,
            color = Dark25,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(50.dp))
        FilledButton(
            text = stringResource(R.string.sign_up),
            onClick = {
                navController.navigate(RouteNames.REGISTER_SCREEN)
            }
        )
        Spacer(modifier = Modifier.height(30.dp))
        FilledButton(
            text = stringResource(R.string.login),
            colors = ButtonDefaults.buttonColors(
                containerColor = Violet20,
                contentColor = Violet100,
            ),
            onClick = {
                navController.navigate(RouteNames.LOGIN_SCREEN)
            }
        )
    }
}


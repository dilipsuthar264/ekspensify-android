package com.memeusix.budgetbuddy.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.navigation.LoginScreenRoute
import com.memeusix.budgetbuddy.navigation.RegisterScreenRoute
import com.memeusix.budgetbuddy.ui.components.FilledButton
import com.memeusix.budgetbuddy.utils.singleClick

@Composable
fun IntroScreen(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(horizontal = 16.dp)
    ) {
        Image(painter = painterResource(R.drawable.ic_hand_money), null)
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            stringResource(R.string.app_name),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            stringResource(R.string.become_your_own_money_manager_and_make_every_rupee_count),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 15.dp)
        )

        Spacer(modifier = Modifier.height(50.dp))
        FilledButton(
            text = stringResource(R.string.sign_up),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ),
            onClick = singleClick {
                navController.navigate(RegisterScreenRoute)
            },
            shape = RoundedCornerShape(16.dp),
            textModifier = Modifier.padding(vertical = 8.dp)

        )
        Spacer(modifier = Modifier.height(30.dp))
        FilledButton(
            text = stringResource(R.string.login),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
            ),
            onClick = singleClick {
                navController.navigate(LoginScreenRoute)
            },
            shape = RoundedCornerShape(16.dp),
            textModifier = Modifier.padding(vertical = 8.dp)
        )
    }
}


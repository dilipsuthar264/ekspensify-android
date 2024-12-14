package com.memeusix.budgetbuddy.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.navigation.AccountScreenRoute
import com.memeusix.budgetbuddy.navigation.BottomNavRoute
import com.memeusix.budgetbuddy.navigation.IntroScreenRoute
import com.memeusix.budgetbuddy.navigation.SplashScreenRoute
import com.memeusix.budgetbuddy.ui.theme.Typography
import com.memeusix.budgetbuddy.utils.SpUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@Composable
fun SplashScreen(
    navController: NavHostController,
    splashViewModel: SplashViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        delay(500)
        if (splashViewModel.spUtils.isLoggedIn && splashViewModel.spUtils.accessToken.isNotEmpty()) {
            if (splashViewModel.spUtils.user?.accounts == 0) {
                navController.navigate(AccountScreenRoute()) {
                    popUpTo(0) { inclusive = true }
                }
            } else {
                navController.navigate(
                    BottomNavRoute
                ) {
                    popUpTo(0) { inclusive = true }
                }
            }
        } else {
            navController.navigate(IntroScreenRoute) {
                popUpTo(SplashScreenRoute) { inclusive = true }
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = Typography.titleLarge.copy(fontSize = 30.sp, fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}


@HiltViewModel
class SplashViewModel @Inject constructor(
    val spUtils: SpUtils
) : ViewModel()
package com.memeusix.budgetbuddy.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.navigation.BottomNavRoute
import com.memeusix.budgetbuddy.navigation.IntroScreenRoute
import com.memeusix.budgetbuddy.navigation.SplashScreenRoute
import com.memeusix.budgetbuddy.ui.theme.Light100
import com.memeusix.budgetbuddy.ui.theme.Typography
import com.memeusix.budgetbuddy.ui.theme.Violet100
import com.memeusix.budgetbuddy.utils.SpUtils
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController, spUtils: SpUtils) {
    LaunchedEffect(Unit) {
        delay(1000)
        if (spUtils.isLoggedIn) {
            //TODO : go to dashboard
        } else {
            navController.navigate(IntroScreenRoute) {
                popUpTo(SplashScreenRoute) { inclusive = true }
            }
        }


    //        navController.navigate(BottomNavRoute) {
//            popUpTo(SplashScreenRoute) { inclusive = true }
//        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Violet100)
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = Typography.titleLarge.copy(fontSize = 30.sp, fontWeight = FontWeight.Bold),
            color = Light100,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}


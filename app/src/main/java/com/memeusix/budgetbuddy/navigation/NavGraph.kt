package com.memeusix.budgetbuddy.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.memeusix.budgetbuddy.data.model.AuthRequestModel
import com.memeusix.budgetbuddy.ui.SplashScreen
import com.memeusix.budgetbuddy.ui.auth.IntroScreen
import com.memeusix.budgetbuddy.ui.auth.LoginScreen
import com.memeusix.budgetbuddy.ui.auth.OtpVerificationScreen
import com.memeusix.budgetbuddy.ui.auth.RegisterScreen
import com.memeusix.budgetbuddy.ui.dashboard.bottomNav.BottomNav
import com.memeusix.budgetbuddy.utils.SpUtils


@Composable
fun NavGraph(navController: NavHostController, spUtils: SpUtils) {
    NavHost(navController = navController, startDestination = SplashScreenRoute) {
        composable<SplashScreenRoute> {
            SplashScreen(navController, spUtils)
        }
        composable<IntroScreenRoute> {
            IntroScreen(navController)
        }
        composable<LoginScreenRoute> {
            LoginScreen(navController)
        }
        composable<RegisterScreenRoute> {
            RegisterScreen(navController)
        }
        composable<OtpVerificationScreenRoute> {
            val args = it.toRoute<OtpVerificationScreenRoute>()
            val authRequestModel = AuthRequestModel(
                name = args.name,
                password = args.password,
                email = args.email
            )
            OtpVerificationScreen(navController = navController, navArgs = authRequestModel)
        }


        // Bottom Nav Screen
        composable<BottomNavRoute> {
            BottomNav(navController)
        }
    }
}
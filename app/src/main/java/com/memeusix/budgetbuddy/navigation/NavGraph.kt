package com.memeusix.budgetbuddy.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.memeusix.budgetbuddy.data.model.requestModel.AuthRequestModel
import com.memeusix.budgetbuddy.ui.SplashScreen
import com.memeusix.budgetbuddy.ui.acounts.AccountsScreen
import com.memeusix.budgetbuddy.ui.acounts.CreateAccountScreen
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
            LoginScreen(navController = navController, spUtils = spUtils)
        }
        composable<RegisterScreenRoute> {
            RegisterScreen(navController = navController, spUtils = spUtils)
        }
        composable<OtpVerificationScreenRoute> {
            val args = it.toRoute<OtpVerificationScreenRoute>()
            val authRequestModel = AuthRequestModel(
                name = args.name,
                email = args.email
            )
            OtpVerificationScreen(
                navController = navController,
                navArgs = authRequestModel,
                spUtils = spUtils
            )
        }


        // Bottom Nav Screen
        composable<BottomNavRoute> {
            BottomNav(navController, spUtils)
        }

        // Account Screens
        composable<AccountScreenRoute> {
            val args = it.toRoute<AccountScreenRoute>()
            AccountsScreen(navController, args)
        }
        composable<CreateAccountScreenRoute> {
            val args = it.toRoute<CreateAccountScreenRoute>()
            CreateAccountScreen(
                navController,
                args
            )
        }
    }
}



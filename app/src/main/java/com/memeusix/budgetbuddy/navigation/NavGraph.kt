package com.memeusix.budgetbuddy.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.memeusix.budgetbuddy.ui.SplashScreen
import com.memeusix.budgetbuddy.ui.auth.IntroScreen
import com.memeusix.budgetbuddy.ui.auth.LoginScreen
import com.memeusix.budgetbuddy.ui.auth.RegisterScreen
import com.memeusix.budgetbuddy.utils.SpUtils

@Composable
fun NavGraph(navController: NavHostController, spUtils: SpUtils) {
    NavHost(navController = navController, startDestination = RouteNames.SPLASH_SCREEN) {
        composable(route = RouteNames.SPLASH_SCREEN) {
            SplashScreen(navController, spUtils)
        }
        composable(route = RouteNames.INTRO_SCREEN){
            IntroScreen(navController)
        }
        composable(route =  RouteNames.LOGIN_SCREEN){
            LoginScreen(navController)
        }
        composable(route= RouteNames.REGISTER_SCREEN){
            RegisterScreen(navController)
        }
    }
}
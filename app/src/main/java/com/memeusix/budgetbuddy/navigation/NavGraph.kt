package com.memeusix.budgetbuddy.navigation

import android.util.Log
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.memeusix.budgetbuddy.data.model.requestModel.AuthRequestModel
import com.memeusix.budgetbuddy.navigation.viewmodel.NavigationViewModel
import com.memeusix.budgetbuddy.navigation.viewmodel.NotificationEventModel
import com.memeusix.budgetbuddy.ui.SplashScreen
import com.memeusix.budgetbuddy.ui.acounts.AccountsScreen
import com.memeusix.budgetbuddy.ui.acounts.CreateAccountScreen
import com.memeusix.budgetbuddy.ui.auth.IntroScreen
import com.memeusix.budgetbuddy.ui.auth.LoginScreen
import com.memeusix.budgetbuddy.ui.auth.OtpVerificationScreen
import com.memeusix.budgetbuddy.ui.auth.RegisterScreen
import com.memeusix.budgetbuddy.ui.categories.CategoriesScreen
import com.memeusix.budgetbuddy.ui.categories.CreateCategoryScreen
import com.memeusix.budgetbuddy.ui.dashboard.bottomNav.BottomNav
import com.memeusix.budgetbuddy.ui.dashboard.budget.BudgetDetailsScreen
import com.memeusix.budgetbuddy.ui.dashboard.budget.BudgetTransactionScreen
import com.memeusix.budgetbuddy.ui.dashboard.budget.CreateBudgetScreen
import com.memeusix.budgetbuddy.ui.dashboard.transactions.CreateTransactionScreen
import com.memeusix.budgetbuddy.ui.dashboard.transactions.FilterScreen
import com.memeusix.budgetbuddy.ui.picture.PicturePreviewScreen
import com.memeusix.budgetbuddy.utils.NotificationActivity


@Composable
fun NavGraph(
    navController: NavHostController,
    navigationViewModel: NavigationViewModel,
    modifier: Modifier,
    callBackNav: (NavDestination?) -> Unit
) {
    val notificationEventState by navigationViewModel.notificationEvent.collectAsStateWithLifecycle()
    var isNavHostReady by remember { mutableStateOf(false) }

    LaunchedEffect(notificationEventState, isNavHostReady) {
        Log.i("NAV", "NavGraph: $notificationEventState")
        if (isNavHostReady) {
            handleNotificationClickNavigation(navController, notificationEventState)
        }
    }
    LaunchedEffect(navController.currentDestination) {
        callBackNav(navController.currentDestination)
    }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = SplashScreenRoute,
        enterTransition = {
            fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {
            fadeOut(
                animationSpec = tween(300)
            )
        },
        exitTransition = { ExitTransition.KeepUntilTransitionsFinished },
        popEnterTransition = { EnterTransition.None },
    ) {

        composable<SplashScreenRoute> {
            SplashScreen(navController)
        }
        composable<IntroScreenRoute> {
            IntroScreen(navController)
        }
        composable<LoginScreenRoute> {
            LoginScreen(navController = navController)
        }
        composable<RegisterScreenRoute> {
            RegisterScreen(navController = navController)
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
            )
        }


        // Bottom Nav Screen
        composable<BottomNavRoute> {
            BottomNav(navController = navController)
            isNavHostReady = true
        }


        // Account Screens
        composable<AccountScreenRoute>(
            deepLinks = listOf(navDeepLink { uriPattern = "budget://account" })
        ) {
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

        // Categories Screens
        composable<CategoriesScreenRoute> {
            CategoriesScreen(navController)
        }

        composable<CreateCategoryScreenRoute> {
            CreateCategoryScreen(
                navController = navController
            )
        }

        // Transaction Screens
        composable<CreateTransactionScreenRoute> {
            val args = it.toRoute<CreateTransactionScreenRoute>()
            CreateTransactionScreen(navController, args)
        }

        composable<FilterScreenRoute> {
            FilterScreen(navController)
        }

        // Budget Screens
        composable<CreateBudgetScreenRoute> {
            CreateBudgetScreen(navController)
        }
        composable<BudgetDetailsScreenRoute> {
            val args = it.toRoute<BudgetDetailsScreenRoute>()
            BudgetDetailsScreen(navController, args)
        }
        composable<BudgetTransactionScreenRoute> {
            val args = it.toRoute<BudgetTransactionScreenRoute>()
            BudgetTransactionScreen(navController, args)
        }

        // Image Preview Screen
        composable<PicturePreviewScreenRoute> {
            val args = it.toRoute<PicturePreviewScreenRoute>()
            PicturePreviewScreen(args)
        }
    }
}

fun handleNotificationClickNavigation(
    navController: NavHostController,
    notificationEventState: NotificationEventModel?
) {

    notificationEventState?.additionalData?.nameValuePairs?.let {
        when (it.activity) {
            NotificationActivity.BUDGET -> {
                // Navigate using deep links
                if (it.id?.isDigitsOnly() == true) {
                    navController.navigate(
                        BudgetDetailsScreenRoute(
                            budgetId = it.id.toInt()
                        )
                    )
                }
            }

            NotificationActivity.TRANSACTION -> {

            }

            else -> Unit
        }
    }
}

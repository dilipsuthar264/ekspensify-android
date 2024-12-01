package com.memeusix.budgetbuddy.navigation

import com.memeusix.budgetbuddy.utils.TransactionType
import kotlinx.serialization.Serializable


@Serializable
object SplashScreenRoute

/**
 * Auth Screens Routes
 */

@Serializable
object IntroScreenRoute

@Serializable
object LoginScreenRoute


@Serializable
object RegisterScreenRoute

@Serializable
data class OtpVerificationScreenRoute(
    val name: String?,
    val email: String?
)


/**
 * DashBoard Routes
 */

@Serializable
object BottomNavRoute

/**
 * Account Screens
 */

@Serializable
data class AccountScreenRoute(
    val isFromProfile: Boolean = false
)

typealias AccountListArgs = String

@Serializable
data class CreateAccountScreenRoute(
    val accountResponseArgs: String? = null,
    val accountList: AccountListArgs? = null,
    val isFromProfile: Boolean = false,
)

// Categories Screen
@Serializable
object CategoriesScreenRoute

@Serializable
data class CreateCategoryScreenRoute(
    val categoryResponseModelArgs: String? = null
)

// Transaction Screens
@Serializable
data class CreateTransactionScreenRoute(
    val transactionType: TransactionType
)

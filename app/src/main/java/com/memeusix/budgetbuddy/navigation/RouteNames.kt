package com.memeusix.budgetbuddy.navigation

import kotlinx.serialization.Serializable


@Serializable
object SplashScreenRoute

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

@Serializable
object BottomNavRoute

@Serializable
object CreateUserScreenRoute

@Serializable
data class TransactionScreenRoute(
    val userId: Int? = null,
)
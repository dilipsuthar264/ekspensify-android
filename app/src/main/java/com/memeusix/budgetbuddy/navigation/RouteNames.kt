package com.memeusix.budgetbuddy.navigation

import kotlinx.serialization.Serializable

//object RouteNames {
//
//    const val SPLASH_SCREEN = "splashscreen"
//    const val INTRO_SCREEN = "introScreen"
//    const val LOGIN_SCREEN = "loginScreen"
//    const val REGISTER_SCREEN = "registerScreen"
//    const val OTP_VERIFICATION_SCREEN = "ogpVerificationScreen"
//
//}

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
    val email: String?,
    val password: String?
)

@Serializable
object BottomNavRoute
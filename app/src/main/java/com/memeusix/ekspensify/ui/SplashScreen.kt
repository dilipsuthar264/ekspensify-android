package com.memeusix.ekspensify.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.memeusix.ekspensify.R
import com.memeusix.ekspensify.data.model.responseModel.UserResponseModel
import com.memeusix.ekspensify.navigation.AccountScreenRoute
import com.memeusix.ekspensify.navigation.BottomNavRoute
import com.memeusix.ekspensify.navigation.IntroScreenRoute
import com.memeusix.ekspensify.navigation.SplashScreenRoute
import com.memeusix.ekspensify.ui.theme.Yellow100
import com.memeusix.ekspensify.ui.theme.fonartoFontFamily
import com.memeusix.ekspensify.utils.SPLASH_DURATION
import com.memeusix.ekspensify.utils.spUtils.SpUtilsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@Composable
fun SplashScreen(
    navController: NavHostController, splashViewModel: SplashViewModel = hiltViewModel()
) {
    val isLogin by splashViewModel.spUtilsManager.isLoggedIn.collectAsState()
    val accessToken by splashViewModel.spUtilsManager.accessToken.collectAsState()
    val user by splashViewModel.spUtilsManager.user.collectAsState()
    val isTypingFinished = remember { mutableStateOf(false) }

//    // Launch the typing effect
//    val typedText = typeWriterText(
//        text = stringResource(R.string.app_name),
//        duration = SPLASH_DURATION,
//        onFinish = {
//            isTypingFinished.value = true
//        }
//    )


    // Lotte animation for logo
    val animationLogoComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.ekspensify_logo_animation))
    val animationLogoProgress by animateLottieCompositionAsState(
        composition = animationLogoComposition,
        iterations = 1,
        speed = 1.5f,
        isPlaying = true,
    )


//     Handle navigation after typing completes
    LaunchedEffect(animationLogoProgress) {
//        if (isTypingFinished.value) {
//            navigateAfterSplash(isLogin, accessToken, user, navController)
//        }
        if (animationLogoProgress == 1f) {
            navigateAfterSplash(isLogin, accessToken, user, navController)
        }
    }
    // System UI configuration
    val systemUiController = rememberSystemUiController()
    DisposableEffect(Unit) {
        val defaultDarkIcons = systemUiController.systemBarsDarkContentEnabled
        systemUiController.apply {
            setStatusBarColor(Color.Transparent, darkIcons = false)
        }

        onDispose {
            systemUiController.apply {
                setStatusBarColor(color = Color.Transparent, darkIcons = defaultDarkIcons)
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
         contentAlignment = Alignment.Center
    ) {
//        Spacer(
//            modifier = Modifier
//                .offset(x = (-20).dp, y = (-10).dp)
//                .blur(60.dp, BlurredEdgeTreatment.Unbounded)
//                .clip(CircleShape)
//                .background(Yellow100, CircleShape)
//                .fillMaxWidth(0.2f)
//                .aspectRatio(1f)
//        )
//        Text(
//            text = typedText,
//            fontFamily = fonartoFontFamily,
//            fontSize = 30.sp,
//            color = MaterialTheme.colorScheme.onPrimary,
//        )
        LottieAnimation(
            composition = animationLogoComposition,
            progress = { animationLogoProgress },
            modifier = Modifier.fillMaxWidth()
        )
    }
}


fun navigateAfterSplash(
    isLogin: Boolean,
    accessToken: String,
    user: UserResponseModel?,
    navController: NavHostController
) {
    println("IS Login  : $isLogin")
    println("accessToekn : $accessToken")
    println(" user : $user")
    when {
        isLogin && accessToken.isNotEmpty() -> {
            val hasNoAccounts = user?.accounts == 0
            if (hasNoAccounts) {
                navController.navigate(AccountScreenRoute()) {
                    popUpTo(0) { inclusive = true }
                }
            } else {
                navController.navigate(BottomNavRoute) {
                    popUpTo(0) { inclusive = true }
                }
            }
        }

        else -> {
            navController.navigate(IntroScreenRoute) {
                popUpTo(SplashScreenRoute) { inclusive = true }
            }
        }
    }
}

@Composable
private fun typeWriterText(
    text: String,
    duration: Long,
    onFinish: () -> Unit
): String {
    var textDisplay by remember { mutableStateOf("") }
    var index by remember { mutableIntStateOf(0) }
    val timePerCharacter = remember { duration / text.length }

    // Launch typing effect
    LaunchedEffect(index) {
        if (index < text.length) {
            textDisplay += text[index].toString()
            delay(timePerCharacter)
            index++
        }
        // Notify when typing is finished
        if (index == text.length) {
            onFinish()
        }
    }

    return textDisplay
}


@HiltViewModel
class SplashViewModel @Inject constructor(
    val spUtilsManager: SpUtilsManager
) : ViewModel()
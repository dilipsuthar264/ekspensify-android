package com.memeusix.budgetbuddy.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
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
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.navigation.AccountScreenRoute
import com.memeusix.budgetbuddy.navigation.BottomNavRoute
import com.memeusix.budgetbuddy.navigation.IntroScreenRoute
import com.memeusix.budgetbuddy.navigation.SplashScreenRoute
import com.memeusix.budgetbuddy.ui.theme.Yellow100
import com.memeusix.budgetbuddy.ui.theme.fonartoFontFamily
import com.memeusix.budgetbuddy.utils.SPLASH_DURATION
import com.memeusix.budgetbuddy.utils.spUtils.SpUtilsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@Composable
fun SplashScreen(
    navController: NavHostController, splashViewModel: SplashViewModel = hiltViewModel()
) {

    val isTypingFinished = remember { mutableStateOf(false) }

    // Launch the typing effect
    val typedText = typeWriterText(
        text = stringResource(R.string.app_name),
        duration = SPLASH_DURATION,
        onFinish = {
            isTypingFinished.value = true
        }
    )


    // Handle navigation after typing completes
    LaunchedEffect(isTypingFinished.value) {
        if (isTypingFinished.value) {
            navigateAfterSplash(splashViewModel, navController)
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
        Spacer(
            modifier = Modifier
                .offset(x = (-20).dp, y = (-10).dp)
                .blur(60.dp, BlurredEdgeTreatment.Unbounded)
                .clip(CircleShape)
                .background(Yellow100, CircleShape)
                .fillMaxWidth(0.2f)
                .aspectRatio(1f)
        )
        Text(
            text = typedText,
            fontFamily = fonartoFontFamily,
            fontSize = 30.sp,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}


fun navigateAfterSplash(
    splashViewModel: SplashViewModel,
    navController: NavHostController
) {
    when {
        splashViewModel.spUtilsManager.isLoggedIn.value && splashViewModel.spUtilsManager.accessToken.value.isNotEmpty() -> {
            val hasNoAccounts = splashViewModel.spUtilsManager.user.value?.accounts == 0
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
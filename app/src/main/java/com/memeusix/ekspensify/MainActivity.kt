package com.memeusix.ekspensify

import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.memeusix.ekspensify.components.OfflineText
import com.memeusix.ekspensify.navigation.NavGraph
import com.memeusix.ekspensify.navigation.SplashScreenRoute
import com.memeusix.ekspensify.navigation.viewmodel.NavigationViewModel
import com.memeusix.ekspensify.ui.theme.EkspensifyTheme
import com.memeusix.ekspensify.utils.internet.InternetChecker
import com.memeusix.ekspensify.utils.smsReceiver.SmsHelper.updateReceiverState
import com.memeusix.ekspensify.utils.spUtils.SpUtilsManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val navigationViewModel by viewModels<NavigationViewModel>()

    @Inject
    lateinit var internetChecker: InternetChecker

    @Inject
    lateinit var spUtilsManager: SpUtilsManager

    override fun getResources(): Resources {
        val baseResources = super.getResources()
        val configuration = Configuration(baseResources.configuration)
        configuration.fontScale = 1.0f
        val newContext = createConfigurationContext(configuration)
        return newContext.resources
    }


    override fun onResume() {
        super.onResume()
        updateReceiverState(this, spUtilsManager.isAutoTrackingEnable.value)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT
            ),
//            navigationBarStyle = SystemBarStyle.auto(
//                android.graphics.Color.TRANSPARENT,
//                android.graphics.Color.TRANSPARENT,
//            )
        )
        setContent {
            DisposableEffect(Unit) {
                internetChecker.startTracking()
                onDispose {
                    internetChecker.stopTracking()
                }
            }
            // Checking for internet connectivity
            val isConnected by internetChecker.isConnected.collectAsState()
            var isSplash by remember { mutableStateOf(true) }
            val navController = rememberNavController()
            EkspensifyTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        NavGraph(navController, navigationViewModel, Modifier.weight(1f)) {
                            isSplash = it?.route == SplashScreenRoute::class.java.canonicalName
                        }
                        OfflineText(!isConnected && !isSplash)
                    }
                }
            }
        }

        setNotificationEvent(intent)
    }


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setNotificationEvent(intent)
    }

    private fun setNotificationEvent(intent: Intent) {
        val notification = intent.extras?.getString("notification")
        Log.i(TAG, "setNotificationEvent: $notification")
        navigationViewModel.setNotificationEvent(notification)
    }


    override fun onPause() {
        super.onPause()
        navigationViewModel.resetNotificationEvent()
    }

    companion object {
        private val TAG = MainActivity::class.java.name
    }

}
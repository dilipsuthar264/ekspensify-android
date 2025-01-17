package com.memeusix.budgetbuddy

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.memeusix.budgetbuddy.utils.isValidUrl
import com.memeusix.budgetbuddy.utils.toJson
import com.onesignal.OneSignal
import com.onesignal.notifications.INotificationClickEvent
import com.onesignal.notifications.INotificationClickListener
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class BudgetBuddy : Application(){


    override fun onCreate() {
        super.onCreate()

        // one signal initialization
        OneSignal.initWithContext(this, BuildConfig.ONESIGNAL_APP_ID)

        // handleClickEvent OneSignal Notification
        handleNotificationClick()
    }

    private fun handleNotificationClick() {
        OneSignal.Notifications.addClickListener(
            object : INotificationClickListener {
                override fun onClick(event: INotificationClickEvent) {
                    if (event.notification.launchURL.isValidUrl()) {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse(event.notification.launchURL)
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                        startActivity(intent)
                    } else {
                        val intent = Intent(
                            this@BudgetBuddy,
                            MainActivity::class.java
                        ).apply {
                            flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
                            val bundle = Bundle().apply {
                                putString("notification", event.notification.toJson())
                            }
                            putExtras(bundle)
                        }
                        startActivity(intent)
                    }
                }
            }
        )
    }

    companion object {
        private val TAG = "BUDGET BUDDY APP"
    }
}
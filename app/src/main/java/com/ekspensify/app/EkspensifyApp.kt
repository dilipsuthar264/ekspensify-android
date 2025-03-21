package com.ekspensify.app

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.ekspensify.app.utils.isValidUrl
import com.ekspensify.app.utils.toJson
import com.onesignal.OneSignal
import com.onesignal.notifications.INotificationClickEvent
import com.onesignal.notifications.INotificationClickListener
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class EkspensifyApp : Application() {


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
                            this@EkspensifyApp,
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
        private val TAG = "Ekspensify APP"
    }
}
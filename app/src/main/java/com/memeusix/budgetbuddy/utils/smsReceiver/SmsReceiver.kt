package com.memeusix.budgetbuddy.utils.smsReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.memeusix.budgetbuddy.utils.smsReceiver.SmsProcessingWorker.Companion

class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val bundle = intent?.extras
        bundle?.let { bundleData ->

            val pdus = bundleData.get("pdus") as Array<*>
            for (pdu in pdus) {
                val smsMessage = SmsMessage.createFromPdu(pdu as ByteArray)
                val messageBody = smsMessage.messageBody

                Log.e(TAG, "doWork: $messageBody", )

                val workRequest = OneTimeWorkRequestBuilder<SmsProcessingWorker>()
                    .setInputData(workDataOf("sms_body" to messageBody))
                    .build()

                if (context != null) {
                    WorkManager.getInstance(context.applicationContext).enqueue(workRequest)
                }
            }
        }
    }


    companion object {
        private const val TAG = "SmsReceiver"
    }
}
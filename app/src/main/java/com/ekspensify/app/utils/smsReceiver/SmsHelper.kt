package com.ekspensify.app.utils.smsReceiver

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import com.ekspensify.app.room.model.PendingTransactionModel
import com.ekspensify.app.ui.acounts.data.BankModel
import com.ekspensify.app.utils.TransactionType
import java.time.LocalDateTime

object SmsHelper {

    private const val TAG = "SMSHelper"

    private val amountPattern =
        "(Rs|INR)?\\s*(\\d+(?:\\.\\d+)?)".toRegex()  // Regex for amount (e.g., Rs 1.00 or INR 1.00)
    private val typePattern =
        "(debited|credited)".toRegex(RegexOption.IGNORE_CASE)  // Regex for type (debit or credit)

    // List of popular bank names in the regex (can be extended as needed)
    private val bankNames = BankModel.getBanks().flatMap { listOf(it.name, it.shortName) }
    private val bankPattern = bankNames.joinToString("|", "(?i)(", ")")


    fun parseSms(smsBody: String): PendingTransactionModel? {
        val amount: Int?
        val type: String?
        val bank: String?
        try {
            // amount fetch
            val amountMatch = amountPattern.find(smsBody)
            val amountString = amountMatch?.groupValues?.get(2) ?: "0.00"
            amount = try {
                val amountDouble = amountString.toDouble()
                if (amountDouble == amountDouble.toInt().toDouble()) {
                    amountDouble.toInt()
                } else {
                    null
                }
            } catch (e: Exception) {
                null
            }

            // type fetch
            val typeMatch = typePattern.find(smsBody)
            type = typeMatch?.value?.uppercase()

            // bank Fetch
            val bankPatternCompiled = bankPattern.toRegex()
            val bankMatch = bankPatternCompiled.find(smsBody)
            bank = bankMatch?.value?.trim()

            return if (amount == null || type == null) {
                null
            } else {
                PendingTransactionModel(
                    amount = amount,
                    type = getTransactionType(type),
                    accountName = bank,
                    description = smsBody,
                    createdAt = LocalDateTime.now().toString()
                )
            }
        } catch (e: Exception) {
            return null
        }
    }


    /*
     * toggle Broadcast receiver Enable disable
     */

    fun updateReceiverState(context: Context, isEnabled: Boolean) {
        val packageManager = context.packageManager
        val componentName = ComponentName(context, SmsReceiver::class.java)

        if (isEnabled) {
            packageManager.setComponentEnabledSetting(
                componentName,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
        } else {
            packageManager.setComponentEnabledSetting(
                componentName,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
        }
    }


    private fun getTransactionType(type: String?): TransactionType? {
        return when (type?.lowercase()) {
            "debited" -> TransactionType.DEBIT
            "credited" -> TransactionType.CREDIT
            else -> null
        }
    }

}

package com.memeusix.budgetbuddy.utils

import android.os.Build
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.TimeZone

fun String?.formatDateTime(format: String = DateFormat.dd_MMM_yyyy_hh_mm_a): String {
    return try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val istZoneId = ZoneId.of("Asia/Kolkata")
            ZonedDateTime.parse(this)
                .withZoneSameInstant(istZoneId)
                .format(DateTimeFormatter.ofPattern(format))
        } else {
            val utcFormat = SimpleDateFormat(DateFormat.INPUT_FORMAT)
            utcFormat.timeZone = TimeZone.getTimeZone("UTC")
            val date = utcFormat.parse(this)
            val istFormat = SimpleDateFormat(format)
            istFormat.timeZone = TimeZone.getTimeZone("Asia/Kolkata")
            istFormat.format(date)
        }
    } catch (e: Exception) {
        ""
    }
}


object DateFormat {
    const val INPUT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    const val dd_MMM_yyyy_hh_mm_a = "dd-MMM-yyyy | hh:mm a"
}
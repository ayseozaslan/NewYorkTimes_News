package com.example.ntnews.utils

import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun formatDateTime(dateTime: String): String {
    val cleanedDateTime = dateTime.replace("Z", "+0000").replace(":", "")
    val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
    val targetFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())

    return try {
        val date = originalFormat.parse(cleanedDateTime)
        targetFormat.format(date ?: Date())
    } catch (e: Exception) {
        dateTime
    }
}
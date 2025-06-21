package com.community.core.common.extensions

import java.text.SimpleDateFormat
import java.util.*

/**
 * Extension functions for String class
 */

/**
 * Check if string is a valid email
 */
fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

/**
 * Check if string is a valid phone number
 */
fun String.isValidPhone(): Boolean {
    return android.util.Patterns.PHONE.matcher(this).matches()
}

/**
 * Capitalize first letter of each word
 */
fun String.toTitleCase(): String {
    return this.split(" ").joinToString(" ") { word ->
        word.lowercase().replaceFirstChar { 
            if (it.isLowerCase()) it.titlecase() else it.toString() 
        }
    }
}

/**
 * Remove extra whitespaces
 */
fun String.trimExtraSpaces(): String {
    return this.trim().replace("\\s+".toRegex(), " ")
}

/**
 * Convert string to safe filename
 */
fun String.toSafeFileName(): String {
    return this.replace("[^a-zA-Z0-9.-]".toRegex(), "_")
}

/**
 * Truncate string to specified length with ellipsis
 */
fun String.truncate(maxLength: Int, ellipsis: String = "..."): String {
    return if (this.length <= maxLength) {
        this
    } else {
        this.substring(0, maxLength - ellipsis.length) + ellipsis
    }
}

/**
 * Format date string from API format to display format
 */
fun String.formatDateFromApi(
    inputFormat: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
    outputFormat: String = "MMM dd, yyyy"
): String {
    return try {
        val inputFormatter = SimpleDateFormat(inputFormat, Locale.getDefault())
        val outputFormatter = SimpleDateFormat(outputFormat, Locale.getDefault())
        val date = inputFormatter.parse(this)
        outputFormatter.format(date ?: Date())
    } catch (e: Exception) {
        this
    }
}

/**
 * Extract initials from name
 */
fun String.getInitials(maxLength: Int = 2): String {
    return this.split(" ")
        .mapNotNull { it.firstOrNull()?.uppercaseChar() }
        .take(maxLength)
        .joinToString("")
}

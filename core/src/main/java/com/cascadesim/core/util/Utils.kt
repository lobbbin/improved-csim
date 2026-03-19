package com.cascadesim.core.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Utility functions for date/time formatting.
 */
object DateTimeUtils {
    private val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    
    fun formatTimestamp(timestamp: Long): String {
        return dateFormat.format(Date(timestamp))
    }
    
    fun formatTime(timestamp: Long): String {
        return timeFormat.format(Date(timestamp))
    }
    
    fun formatDuration(millis: Long): String {
        val seconds = millis / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        
        return when {
            days > 0 -> "$days day${if (days > 1) "s" else ""}"
            hours > 0 -> "$hours hour${if (hours > 1) "s" else ""}"
            minutes > 0 -> "$minutes minute${if (minutes > 1) "s" else ""}"
            else -> "$seconds seconds"
        }
    }
    
    fun formatGameTime(year: Int, month: Int, day: Int): String {
        val monthNames = listOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )
        return "$day ${monthNames[(month - 1).coerceIn(0, 11)].take(3)} $year"
    }
}

/**
 * Utility functions for number formatting.
 */
object NumberUtils {
    fun formatNumber(number: Long): String {
        return when {
            number >= 1_000_000_000 -> "%.1fB".format(number / 1_000_000_000.0)
            number >= 1_000_000 -> "%.1fM".format(number / 1_000_000.0)
            number >= 1_000 -> "%.1fK".format(number / 1_000.0)
            else -> number.toString()
        }
    }
    
    fun formatCurrency(amount: Long, symbol: String = "$"): String {
        return "$symbol${formatNumber(amount)}"
    }
    
    fun formatPercentage(value: Double, decimals: Int = 1): String {
        return "%.${decimals}f%%".format(value * 100)
    }
}

/**
 * Utility for generating unique IDs.
 */
object IdGenerator {
    fun generate(): String = UUID.randomUUID().toString()
    
    fun generateShort(): String = UUID.randomUUID().toString().take(8)
}

/**
 * Result wrapper for async operations.
 */
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
    
    inline fun <R> map(transform: (T) -> R): Result<R> = when (this) {
        is Success -> Success(transform(data))
        is Error -> this
    }
    
    inline fun onSuccess(action: (T) -> Unit): Result<T> {
        if (this is Success) action(data)
        return this
    }
    
    inline fun onError(action: (Throwable) -> Unit): Result<T> {
        if (this is Error) action(exception)
        return this
    }
}

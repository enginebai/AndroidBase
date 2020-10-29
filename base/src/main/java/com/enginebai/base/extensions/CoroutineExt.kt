package com.enginebai.base.extensions

import kotlinx.coroutines.delay
import timber.log.Timber

/**
 * Retry running block with exponential backoff mechanism.
 * @param times how many times to retry.
 * @param initialDelayMillis The initial delay time in millis second.
 * @param delayFactor the factor to multiple [initialDelayMillis] to be next retry delay
 */
suspend fun <T> retry(
    times: Int = 5,
    initialDelayMillis: Long = 1000,
    delayFactor: Double = 2.0,
    block: suspend () -> T
): T {
    var currentDelay = initialDelayMillis
    repeat(times) {
        try {
            return block()
        } catch (e: Exception) {
            Timber.w(e)
        }
        delay(currentDelay)
        currentDelay = (currentDelay * delayFactor).toLong()
    }
    return block() // last attempt
}
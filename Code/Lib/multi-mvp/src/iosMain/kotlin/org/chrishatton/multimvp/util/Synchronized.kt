package org.chrishatton.multimvp.util

/**
 * WARNING: This dummy implementation does not actually perform any synchronization.
 * At the time of writing, Kotlin/Native doesn't support semaphore locking and
 * alternative strategies must be employed - simplest being only run on a single thread!
 * TODO Implement synchronization when Kotlin/Native supports it
 */
actual fun <T> synchronized(lock: Any, block: () -> T): T = block()
package org.chrishatton.multimvp.util

actual fun <T> synchronized(lock: Any, block:()->T): T
        = kotlin.synchronized(lock,block)

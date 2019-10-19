package org.chrishatton.multimvp.util

expect fun <T> synchronized(lock: Any, block:()->T ) : T
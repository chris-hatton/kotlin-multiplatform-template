package org.chrishatton.multimvp.util

import kotlinx.coroutines.CoroutineDispatcher

expect val uiDispatcher : CoroutineDispatcher

expect val processDispatcher : CoroutineDispatcher

expect val ioDispatcher : CoroutineDispatcher
package org.chrishatton.multimvp.util

import kotlinx.coroutines.*

@InternalCoroutinesApi
@kotlinx.coroutines.ExperimentalCoroutinesApi
actual val uiDispatcher : CoroutineDispatcher = Dispatchers.Main

@InternalCoroutinesApi
@kotlinx.coroutines.ExperimentalCoroutinesApi
actual val processDispatcher : CoroutineDispatcher = Dispatchers.Default

actual val ioDispatcher : CoroutineDispatcher = Dispatchers.IO

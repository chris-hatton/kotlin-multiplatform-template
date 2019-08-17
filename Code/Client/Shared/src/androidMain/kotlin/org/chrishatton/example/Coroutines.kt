package org.chrishatton.example

import kotlinx.coroutines.*
import java.util.concurrent.*
import kotlin.coroutines.CoroutineContext

actual val uiScope = Dispatchers.Main.createScope()

actual val processScope = ThreadPoolExecutor(1, 4, 1, TimeUnit.MINUTES, LinkedBlockingQueue<Runnable>())
    .asCoroutineDispatcher()
    .createScope()

actual val netScope = Dispatchers.IO.createScope()

private fun CoroutineDispatcher.createScope() = object : CoroutineScope {
    private val dispatcher = this@createScope
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = dispatcher + job
}

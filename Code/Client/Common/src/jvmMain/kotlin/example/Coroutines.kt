package example

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

actual val uiScope = object : CoroutineScope {
    private val dispatcher = Dispatchers.Main
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = dispatcher + job
}

actual val netScope = object : CoroutineScope {
    private val dispatcher = Dispatchers.IO
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = dispatcher + job
}

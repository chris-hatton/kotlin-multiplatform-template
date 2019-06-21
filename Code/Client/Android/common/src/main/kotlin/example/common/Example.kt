package example.common

import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

object Example {
    val httpClient : HttpClient by lazy {
        HttpClient()
    }
}

internal class MainScope: CoroutineScope {
    private val dispatcher = Dispatchers.Main
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = dispatcher + job
}
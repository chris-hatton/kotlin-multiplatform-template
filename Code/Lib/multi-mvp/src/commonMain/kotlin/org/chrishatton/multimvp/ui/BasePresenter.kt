package org.chrishatton.multimvp.ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BasePresenter<out V: Contract.View<Self, V>,Self: Contract.Presenter<V, Self>>(override val view : V) : Contract.Presenter<V,Self>,
    CoroutineScope {

    private var _coroutineContext : CoroutineContext? = null
    private var _job : Job? = null

    final override val coroutineContext: CoroutineContext
        get() = _coroutineContext ?: throw IllegalStateException("Attempt to access coroutineContext while this Fragment is detached")

    override fun start() {
        val job = Job().also { _job = it }
        _coroutineContext = Dispatchers.Default + job
    }

    override fun stop() {
        _job?.cancel() ?: throw IllegalStateException("Call to stop() but the presenter was not start()ed")
        _job = null
    }
}
package org.chrishatton.multimvp.ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

abstract class BasePresenter<out V: Contract.View<Self, V>,Self: Contract.Presenter<V, Self>>(override val view : V) : Contract.Presenter<V,Self> {

    override var lifecycleScope: CoroutineScope? = null

    override fun start() {
        require(lifecycleScope==null)
        lifecycleScope = CoroutineScope(Dispatchers.Default)
    }

    override fun stop() {
        require(lifecycleScope!=null)
        lifecycleScope?.cancel("${this::class.simpleName} lifecycle end")
        lifecycleScope = null
    }
}
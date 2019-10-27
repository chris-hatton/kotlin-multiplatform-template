package org.chrishatton.multimvp.ui

import kotlinx.coroutines.*
import org.chrishatton.multimvp.util.DefaultScope
import org.chrishatton.multimvp.util.processDispatcher
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@FlowPreview
abstract class BasePresenter<out V: Contract.View<Self, V>,Self: Contract.Presenter<V, Self>>(override val view : V) : Contract.Presenter<V,Self>,
        Cycleable by CycleableMixIn(scopeCreator = { CoroutineScope(processDispatcher) } )
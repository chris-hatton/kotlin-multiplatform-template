package org.chrishatton.multimvp.ui

import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren

abstract class BasePresenter<V: Contract.View<Self, V>,Self: Contract.Presenter<V, Self>>(override val view : V) : Contract.Presenter<V,Self> {

    protected var job : Job? = null
        private set

    override fun onAttach() {
        job = Job()
    }

    override fun onDetach() {
        job?.cancelChildren()
    }
}
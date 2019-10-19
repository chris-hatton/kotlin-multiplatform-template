package org.chrishatton.multimvp.ui

import kotlinx.coroutines.CoroutineScope

/**
 * The basic Contract for all MVP Presenters/Views.
 */
interface Contract {

    val lifecycleScope : CoroutineScope?

    interface Presenter<out V: View<Self, V>, out Self: Presenter<V, Self>> : Contract, Cycleable {
        val view : V
    }

    interface View<out P: Presenter<Self, P>, out Self: View<P, Self>> : Contract {
        val presenter : P
    }
}

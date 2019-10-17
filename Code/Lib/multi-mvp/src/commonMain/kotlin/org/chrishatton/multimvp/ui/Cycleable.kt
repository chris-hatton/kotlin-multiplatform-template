package org.chrishatton.multimvp.ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

/**
 * Interface for objects which are `start`ed, `stop`ped and have a `CoroutineScope` tied to this cycle.
 * The motivation for separating this concern is to enable multiple View base-classes across a few
 * different platforms to 'inherit' this behaviour by the 'mix-in inheritance' offered by Kotlin's
 * [delegation feature][https://kotlinlang.org/docs/reference/delegation.html].
 */
interface Cycleable {

    class OutOfPhaseException(message:String) : Exception(message)

    enum class Phase {
        Started,
        Stopped
    }

    val phaseFlow : Flow<Phase>

    val lifecycleScope : CoroutineScope?

    fun start()
    fun stop()
}

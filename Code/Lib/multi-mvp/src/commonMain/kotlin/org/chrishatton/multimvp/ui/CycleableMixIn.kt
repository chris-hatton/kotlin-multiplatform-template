package org.chrishatton.multimvp.ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.chrishatton.multimvp.util.synchronized as synchronized

private object LifecycleLock

@FlowPreview
@ExperimentalCoroutinesApi
class CycleableMixIn(
    val name         : String? = null,
    val scopeCreator : ()->CoroutineScope
) : Cycleable {

    private val phaseChannel = ConflatedBroadcastChannel(Cycleable.Phase.Stopped)
    override val phaseFlow: Flow<Cycleable.Phase> = phaseChannel.asFlow()

    /**
     * Expected to be called when the owning [Cycleable] object is `start()'ed`.
     */
    override fun start() {
        synchronized(lock = LifecycleLock) {
            if(phaseChannel.value == Cycleable.Phase.Started) {
                throw Cycleable.OutOfPhaseException("Received onStart but was already started!")
            }
            require(lifecycleScope == null)
            phaseChannel.offer(Cycleable.Phase.Started)
            lifecycleScope = scopeCreator()
        }
    }

    /**
     * Expected to be called when the owning [Cycleable] object is `stop()'ed`.
     */
    override fun stop() {
        synchronized(lock = LifecycleLock) {
            if(phaseChannel.value == Cycleable.Phase.Stopped) {
                throw Cycleable.OutOfPhaseException("Received onStop but was already stopped!")
            }
            require(lifecycleScope != null)
            phaseChannel.offer(Cycleable.Phase.Stopped)
            lifecycleScope?.cancel("The lifecycle of ${name ?: "unnamed object"} is ending.")
            lifecycleScope = null
        }
    }

    /**
     * [CoroutineScope] which is managed to be non-null when the owning [Cycleable] is started,
     * and null when the [Cycleable] is stopped.
     */
    override var lifecycleScope: CoroutineScope? = null
}
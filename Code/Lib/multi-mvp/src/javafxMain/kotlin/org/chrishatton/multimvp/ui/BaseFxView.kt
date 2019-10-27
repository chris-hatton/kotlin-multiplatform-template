package org.chrishatton.multimvp.ui

import javafx.scene.Parent
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import org.chrishatton.multimvp.util.uiDispatcher

/**
 * Base for JavaFX Views which follow the Multiplatorm-template's MVP convention:
 * - Implements a ViewContract
 * - Provides a PresenterContract implementation (a Presenter), corresponding 1:1 to the ViewContract
 * - Issues lifecycle callbacks to the Presenter
 */
@InternalCoroutinesApi
@FlowPreview
@ExperimentalCoroutinesApi
abstract class BaseFxView<VC: Contract.View<PC, VC>,PC: Contract.Presenter<VC, PC>> : Contract.View<PC, VC>,
    Cycleable {

    private val cycleableMixIn = CycleableMixIn(scopeCreator =  { CoroutineScope(uiDispatcher) } )

    abstract val root : Parent

    override fun start() {
        presenter.start()
        cycleableMixIn.start()
    }

    override fun stop() {
        presenter.stop()
        cycleableMixIn.stop()
    }

    override val phaseFlow      : Flow<Cycleable.Phase> get() = cycleableMixIn.phaseFlow
    override val lifecycleScope : CoroutineScope?       get() = cycleableMixIn.lifecycleScope
}
package org.chrishatton.example

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.MainScope
import org.chrishatton.multimvp.ui.Contract
import org.chrishatton.multimvp.ui.Cycleable
import org.chrishatton.multimvp.ui.CycleableMixIn
import tornadofx.*

/**
 * Base for JavaFX Views which follow the Multiplatorm-template's MVP convention:
 * - Implements a ViewContract
 * - Provides a PresenterContract implementation (a Presenter), corresponding 1:1 to the ViewContract
 * - Issues lifecycle callbacks to the Presenter
 */
@FlowPreview
@ExperimentalCoroutinesApi
abstract class BaseView<VC: Contract.View<PC, VC>,PC: Contract.Presenter<VC, PC>>() : View(), Contract.View<PC, VC>,
    Cycleable by CycleableMixIn(scopeCreator = ::MainScope) {

    override fun onDock() {
        super.onDock()
        presenter.start()
    }

    override fun onUndock() {
        super.onUndock()
        presenter.stop()
    }

}
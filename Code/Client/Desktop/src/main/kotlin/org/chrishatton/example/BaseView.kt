package org.chrishatton.example

import org.chrishatton.example.ui.contract.Contract
import tornadofx.*

/**
 * Base for JavaFX Views which follow the Multiplatorm-template's MVP convention:
 * - Implements a ViewContract
 * - Provides a PresenterContract implementation (a Presenter), corresponding 1:1 to the ViewContract
 * - Issues lifecycle callbacks to the Presenter
 */
abstract class BaseView<VC: Contract.View<PC, VC>,PC: Contract.Presenter<VC, PC>> : View(), Contract.View<PC, VC> {

    override fun onDock() {
        super.onDock()
        presenter.onAttach()
    }

    override fun onUndock() {
        super.onUndock()
        presenter.onDetach()
    }

}
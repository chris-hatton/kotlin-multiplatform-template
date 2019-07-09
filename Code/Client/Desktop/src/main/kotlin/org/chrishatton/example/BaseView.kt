package org.chrishatton.example

import org.chrishatton.example.ui.PresenterContract
import org.chrishatton.example.ui.ViewContract
import tornadofx.*

/**
 * Base for JavaFX Views which follow the Multiplatorm-template's MVP convention:
 * - Implements a ViewContract
 * - Provides a PresenterContract implementation (a Presenter), corresponding 1:1 to the ViewContract
 * - Issues lifecycle callbacks to the Presenter
 */
abstract class BaseView<VC: ViewContract<PC, VC>,PC: PresenterContract<VC, PC>> : View(),
        ViewContract<PC, VC> {

    override fun onDock() {
        super.onDock()
        presenter.onAttach()
    }

    override fun onUndock() {
        super.onUndock()
        presenter.onDetach()
    }

}
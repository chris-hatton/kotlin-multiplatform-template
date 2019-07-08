package org.chrishatton.example

import org.chrishatton.example.ui.PresenterContract
import org.chrishatton.example.ui.ViewContract
import tornadofx.*

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
package org.chrishatton.multimvp.ui

import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.MainScope
import org.chrishatton.multimvp.util.fxml

/**
 * Base for JavaFX Views which follow the Multiplatorm-template's MVP convention:
 * - Implements a ViewContract
 * - Provides a PresenterContract implementation (a Presenter), corresponding 1:1 to the ViewContract
 * - Issues lifecycle callbacks to the Presenter
 */
@FlowPreview
@ExperimentalCoroutinesApi
abstract class BaseFxView<VC: Contract.View<PC, VC>,PC: Contract.Presenter<VC, PC>> : Contract.View<PC, VC>,
    Cycleable by CycleableMixIn(scopeCreator = ::MainScope) {

    abstract val root : Parent

    override fun start() {
        presenter.start()
    }

    override fun stop() {
        presenter.stop()
    }
}
package org.chrishatton.example

import javafx.scene.Parent
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import org.chrishatton.example.ui.FirstPresenter
import org.chrishatton.example.ui.contract.FirstContract
import tornadofx.*

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class FirstView : BaseView<FirstContract.View,FirstContract.Presenter>(), FirstContract.View {

    override val root : Parent by fxml()

    val submitButton : Button    by fxid()
    val nameField    : TextField by fxid()
    val replyLabel   : Label     by fxid()

    override fun displayGreeting(text: String) {
        uiScope.launch {
            replyLabel.text = text
        }
    }

    override val presenter: FirstContract.Presenter by lazy {
         FirstPresenter(baseUrl = "http://localhost:8080", view = this)
    }

    override fun onDock() {
        super.onDock()
        submitButton.setOnAction {
            val name = nameField.text
            processScope.launch {
                presenter.didSetName(name)
            }
        }
    }

    override fun onUndock() {
        super.onUndock()
        submitButton.setOnAction {}
    }
}
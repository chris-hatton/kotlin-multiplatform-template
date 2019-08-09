package org.chrishatton.example

import javafx.scene.Parent
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TabPane
import javafx.scene.control.TextField
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.StackPane
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import org.chrishatton.example.model.Validated
import org.chrishatton.example.ui.FirstPresenter
import org.chrishatton.example.ui.contract.FirstContract
import org.chrishatton.example.ui.contract.LoginContract
import tornadofx.*

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class LoginView : BaseView<LoginContract.View,LoginContract.Presenter>(), LoginContract.View {

    override val validatedUsernameCollector : FlowCollector<Validated<String>> = object : FlowCollector<Validated<String>> {
        override suspend fun emit(value: Validated<String>) {

        }
    }

    override val validatedPasswordCollector: FlowCollector<Validated<String>> = object : FlowCollector<Validated<String>> {
        override suspend fun emit(value: Validated<String>) {

        }
    }

    override val validatedConfirmPasswordCollector: FlowCollector<Validated<String>> = object : FlowCollector<Validated<String>> {
        override suspend fun emit(value: Validated<String>) {

        }
    }

    override val root : Parent by fxml()

    val stackPane    : StackPane  by fxid()
    val tabPane      : TabPane    by fxid()
    val progressPane : AnchorPane by fxid()

    // Login tab


    // Register tab
    val registerButton                   : Button    by fxid()
    val passwordRegisterTextField        : TextField by fxid()
    val confirmPasswordRegisterTextField : TextField by fxid()
    val replyRegisterLabel               : Label     by fxid()

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
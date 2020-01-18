package org.chrishatton.example

import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TabPane
import javafx.scene.control.TextField
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.StackPane
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import org.chrishatton.example.model.Validated
import org.chrishatton.example.ui.LoginPresenter
import org.chrishatton.example.ui.contract.LoginContract
import org.chrishatton.multimvp.ui.BaseFxmlView
import org.chrishatton.multimvp.util.fxid

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class LoginView : BaseFxmlView<LoginContract.View,LoginContract.Presenter>(), LoginContract.View {

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

    val stackPane    : StackPane  by fxid()
    val tabPane      : TabPane    by fxid()
    val progressPane : AnchorPane by fxid()

    // Login tab

    // Register tab
    val registerButton                   : Button    by fxid()
    val passwordRegisterTextField        : TextField by fxid()
    val confirmPasswordRegisterTextField : TextField by fxid()
    val replyRegisterLabel               : Label     by fxid()

    override val presenter: LoginContract.Presenter by lazy {
        LoginPresenter(view = this)
    }

//    override fun onDock() {
//        super.onDock()
//        submitButton.setOnAction {
//            val name = nameField.text
//            processScope.launch {
//                presenter.didSetName(name)
//            }
//        }
//    }

//    override fun onUndock() {
//        super.onUndock()
//        submitButton.setOnAction {}
//    }

    override val loginUsername: Flow<String>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val loginPassword: Flow<String>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val registerUsername: Flow<String>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val registerPassword: Flow<String>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val registerConfirmPassword: Flow<String>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val registrationSubmits: BroadcastChannel<Unit>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val loginSubmits: BroadcastChannel<Unit>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

}
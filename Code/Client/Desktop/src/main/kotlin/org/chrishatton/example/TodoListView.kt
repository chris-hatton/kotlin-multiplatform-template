package org.chrishatton.example

import javafx.scene.Parent
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import kotlinx.coroutines.*
import org.chrishatton.example.ui.TodoListPresenter
import org.chrishatton.multimvp.ui.BaseFxmlView
import org.chrishatton.multimvp.util.fxid
import org.chrishatton.example.ui.TodoListContract.View as View
import org.chrishatton.example.ui.TodoListContract.Presenter as Presenter

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class TodoListView : BaseFxmlView<View, Presenter>(), View {

    val submitButton : Button    by fxid()
    val nameField    : TextField by fxid()
    val replyLabel   : Label     by fxid()

    override fun displayGreeting(text: String) {
        lifecycleScope!!.launch {
            replyLabel.text = text
        }
    }

    override val presenter: Presenter by lazy {
         TodoListPresenter(baseUrl = "http://localhost:8080", view = this)
    }

    override fun start() {
        super.start()
        submitButton.setOnAction {
            val name = nameField.text
            lifecycleScope!!.launch {
                presenter.didSetName(name)
            }
        }
    }

    override fun stop() {
        super.stop()
        submitButton.setOnAction {}
    }
}
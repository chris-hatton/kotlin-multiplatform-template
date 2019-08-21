package org.chrishatton.coroutinesui.ui

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.ButtonBase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel

@ExperimentalCoroutinesApi
fun ButtonBase.actionEvents() : Channel<ActionEvent> {
    val actionEvents = Channel<ActionEvent>()
    this@actionEvents.onAction = EventHandler { actionEvent ->
        actionEvents.offer(actionEvent)
    }
    actionEvents.invokeOnClose {
        this@actionEvents.onAction = null
    }
    return actionEvents
}


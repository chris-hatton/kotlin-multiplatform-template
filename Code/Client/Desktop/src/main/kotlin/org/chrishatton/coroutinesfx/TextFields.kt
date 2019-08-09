package org.chrishatton.coroutinesfx

import javafx.scene.control.TextField
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel

@ExperimentalCoroutinesApi
fun TextField.values() : BroadcastChannel<String> = textProperty().valuesBroadcast()
package org.chrishatton.coroutinesui.ui

import javafx.beans.property.Property
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.asFlow

data class Change<T>(val observable:ObservableValue<out T>, val oldValue: T, val newValue: T)

@ExperimentalCoroutinesApi
fun <T> Property<T>.changes() : Channel<Change<T>> {
    val changes = Channel<Change<T>>()
    val listener = ChangeListener<T> { observable, oldValue, newValue ->
        changes.offer(Change(observable, oldValue, newValue))
    }
    addListener(listener)
    changes.invokeOnClose { removeListener(listener) }
    return changes
}

@ExperimentalCoroutinesApi
fun <T> Property<T>.valueChanges() : Channel<T> {
    val values = Channel<T>()
    val listener = ChangeListener<T> { _,_,newValue ->
        values.offer(newValue)
    }
    addListener(listener)
    values.invokeOnClose { removeListener(listener) }
    return values
}

@ExperimentalCoroutinesApi
fun <T> Property<T>.valuesBroadcast() : BroadcastChannel<T> {
    val values = ConflatedBroadcastChannel<T>(value)
    val listener = ChangeListener<T> { _, _, newValue ->
        values.offer(newValue)
    }
    addListener(listener)
    values.invokeOnClose { removeListener(listener) }
    return values
}

@ExperimentalCoroutinesApi
fun <T> Property<T>.values() : ReceiveChannel<T> = valuesBroadcast().openSubscription()

@FlowPreview
@ExperimentalCoroutinesApi
fun <T> Property<T>.asFlow() : Flow<T> = valuesBroadcast().asFlow()

fun <T> Property<T>.asCollector() : FlowCollector<T> = object : FlowCollector<T> {
    override suspend fun emit(value: T) {
        this@asCollector.value = value
    }
}

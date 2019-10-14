package org.chrishatton.coroutinesui.ui

import android.view.View
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import java.util.*

@ExperimentalCoroutinesApi
private val clicksChannels : WeakHashMap<View, BroadcastChannel<Unit>> by lazy {
    WeakHashMap<View, BroadcastChannel<Unit>>(16)
}

@ExperimentalCoroutinesApi
fun View.clicksChannel() : BroadcastChannel<Unit> {
    return clicksChannels[this] ?: BroadcastChannel<Unit>(1).apply {
        setOnClickListener { offer(Unit) }
        invokeOnClose {
            setOnClickListener(null)
            clicksChannels.remove(this@clicksChannel)
        }
    }.also { channel -> clicksChannels[this] = channel }
}

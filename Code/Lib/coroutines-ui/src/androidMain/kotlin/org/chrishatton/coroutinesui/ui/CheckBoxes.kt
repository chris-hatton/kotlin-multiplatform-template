package org.chrishatton.coroutinesui.ui

import android.os.Looper
import android.view.View
import android.widget.CheckBox
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import java.util.*

@ExperimentalCoroutinesApi
private val isCheckedChannels : WeakHashMap<CheckBox,BroadcastChannel<Boolean>> by lazy {
    WeakHashMap<CheckBox,BroadcastChannel<Boolean>>(16)
}

@ExperimentalCoroutinesApi
fun CheckBox.isCheckedChannel() : BroadcastChannel<Boolean> {
    return isCheckedChannels[this] ?: ConflatedBroadcastChannel(isChecked).apply {
        setOnCheckedChangeListener { _, isChecked -> offer(isChecked) }
        invokeOnClose {
            setOnCheckedChangeListener(null)
            isCheckedChannels.remove(this@isCheckedChannel)
        }
    }.also { channel -> isCheckedChannels[this] = channel }
}

package org.chrishatton.coroutinesui.ui

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import java.util.*

@ExperimentalCoroutinesApi
private val textChannels : WeakHashMap<EditText, ConflatedBroadcastChannel<CharSequence>> by lazy {
    WeakHashMap<EditText, ConflatedBroadcastChannel<CharSequence>>(16)
}

@ExperimentalCoroutinesApi
fun EditText.textChannel() : BroadcastChannel<CharSequence> {
    return textChannels[this] ?: ConflatedBroadcastChannel<CharSequence>(text.toString()).apply {

        val listener = object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                offer(p0 ?: "")
            }
        }

        addTextChangedListener(listener)
        invokeOnClose {
            removeTextChangedListener(listener)
            textChannels.remove(this@textChannel)
        }
    }.also { channel -> textChannels[this] = channel }
}

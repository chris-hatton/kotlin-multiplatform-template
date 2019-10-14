package org.chrishatton.multimvp.ui

import android.app.Activity
import android.content.Context
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ReceiveChannel
import kotlin.coroutines.CoroutineContext

/**
 * Views on Android are typically backed by either an Activity of a Fragment.
 * This convenience accessor returns the Android Context for either.
 */
val BaseView.androidContext : Context? get() = when(this) {
    is Fragment -> this.context
    is Activity -> this
    else -> null
}

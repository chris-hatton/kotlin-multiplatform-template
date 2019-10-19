package org.chrishatton.multimvp.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

fun DefaultScope() : CoroutineScope = CoroutineScope(Dispatchers.Default)
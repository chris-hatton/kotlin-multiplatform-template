package org.chrishatton.example

import kotlinx.coroutines.CoroutineScope

expect val uiScope : CoroutineScope

expect val processScope : CoroutineScope

expect val netScope : CoroutineScope
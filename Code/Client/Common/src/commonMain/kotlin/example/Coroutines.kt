package example

import kotlinx.coroutines.CoroutineScope

expect val uiScope : CoroutineScope

expect val netScope : CoroutineScope
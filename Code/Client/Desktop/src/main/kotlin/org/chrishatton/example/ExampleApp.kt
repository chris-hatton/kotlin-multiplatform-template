
package org.chrishatton.example

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import tornadofx.*

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class ExampleApp : App(FirstView::class, Styles::class)


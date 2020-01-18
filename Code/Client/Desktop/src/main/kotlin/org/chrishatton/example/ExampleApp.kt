
package org.chrishatton.example

import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage
import kotlinx.coroutines.*
import org.chrishatton.multimvp.ui.BaseFxmlView
import org.chrishatton.multimvp.ui.Cycleable
import org.chrishatton.multimvp.ui.CycleableMixIn
import org.chrishatton.multimvp.util.processDispatcher

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class ExampleApp : Application(), Cycleable by CycleableMixIn( scopeCreator = {
    CoroutineScope( processDispatcher + SupervisorJob() )
} ) {

    private lateinit var view : BaseFxmlView<*,*>

    override fun start(primaryStage: Stage) {
        primaryStage.title = "Hello World!"

        view = TodoListView()

        primaryStage.scene = Scene(view.root, 640.0, 480.0)
        primaryStage.show()

        view.start()
    }

    override fun stop() {
        view.stop()
        super.stop()
    }
}


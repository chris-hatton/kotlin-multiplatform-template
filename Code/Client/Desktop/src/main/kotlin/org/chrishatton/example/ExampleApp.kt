
package org.chrishatton.example

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import kotlinx.coroutines.*
import org.chrishatton.multimvp.ui.BaseFxmlView
import org.chrishatton.multimvp.ui.BaseView
import org.chrishatton.multimvp.ui.Cycleable
import org.chrishatton.multimvp.ui.CycleableMixIn
import org.chrishatton.multimvp.util.processDispatcher
import java.io.InputStream

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class ExampleApp : Application(), Cycleable by CycleableMixIn( scopeCreator = {
    CoroutineScope( processDispatcher + SupervisorJob() )
} ) {

    private lateinit var view : BaseFxmlView<*,*>

    override fun start(primaryStage: Stage) {
        primaryStage.title = "Hello World!"

        view = FirstView()

        primaryStage.scene = Scene(view.root, 640.0, 480.0)
        primaryStage.show()

        view.start()
    }

    override fun stop() {
        view.stop()
        super.stop()
    }
}


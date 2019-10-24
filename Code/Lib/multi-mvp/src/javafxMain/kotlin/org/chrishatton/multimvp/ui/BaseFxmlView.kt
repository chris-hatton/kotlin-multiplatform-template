package org.chrishatton.multimvp.ui

import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.MainScope
import org.chrishatton.multimvp.util.fxml

/**
 * Base for JavaFX Views which load from an FXML file.
 * In this case, Multi-MVP imposes a convention that the FXML file must nominate a
 * JavaFX Controller class which is the Multi-MVP Presenter.
 *
 * Failing to nominate a Controller in your FXML file, or nominating one which
 * is not an instance of PC, will result in an error.
 */
@FlowPreview
@ExperimentalCoroutinesApi
abstract class BaseFxmlView<VC: Contract.View<PC, VC>,PC: Contract.Presenter<VC, PC>>(
    name: String? = null
) : BaseFxView<VC, PC>() {

    final override val root : Parent by fxml(name)

    val fxmlLoader by lazy(::FXMLLoader)

    override val presenter: PC by lazy {
        fxmlLoader.getController<PC>()
    }
}
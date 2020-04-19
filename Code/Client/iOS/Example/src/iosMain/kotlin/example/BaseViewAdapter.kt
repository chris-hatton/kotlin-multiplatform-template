package example

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.chrishatton.multimvp.ui.Contract.Presenter as Presenter
import org.chrishatton.multimvp.ui.Contract.View as View

/**
 * We would prefer to use a BaseViewController but 'Non-final Kotlin subclasses of Objective-C classes are not yet supported'
 * TODO: Refactor if/when this feature becomes available
 * Using this for now to satisfy *some* UIViewController boilerplate.
 */
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
abstract class BaseViewAdapter<
        Self:BaseViewAdapter<Self,VC,PC>,
        VC: View<PC, VC>,
        PC: Presenter<VC, PC>
    > : View<PC, VC> {

    fun viewWillAppear(animated: Boolean) {
        val somePresenter : PC = presenter
        somePresenter.start()
    }

    fun viewDidDisappear(animated: Boolean) {
        presenter.stop()
    }
}
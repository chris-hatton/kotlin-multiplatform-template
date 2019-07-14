package example

import org.chrishatton.example.ui.PresenterContract
import org.chrishatton.example.ui.ViewContract

/**
 * We would prefer to use a BaseViewController but 'Non-final Kotlin subclasses of Objective-C classes are not yet supported'
 * TODO: Refactor if/when this feature becomes available
 * Using this for now to satisfy *some* UIViewController boilerplate.
 */
abstract class BaseViewAdapter<
        Self:BaseViewAdapter<Self,VC,PC>,
        VC: ViewContract<PC, VC>,
        PC: PresenterContract<VC, PC>
    > : ViewContract<PC, VC> {

    fun viewWillAppear(animated: Boolean) {
        presenter.onAttach()
    }

    fun viewDidDisappear(animated: Boolean) {
        presenter.onDetach()
    }
}
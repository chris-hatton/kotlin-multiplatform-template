
// We would like to do this but 'Non-final Kotlin subclasses of Objective-C classes are not yet supported'
// TODO: Refactor if/when this feature becomes available
// For now use BaseViewAdapter to satisfy UIViewController boilerplate.

/*
package example

import example.ui.PresenterContract
import example.ui.ViewContract
import platform.Foundation.NSCoder
import platform.UIKit.UIViewController

abstract class BaseViewController<VC: ViewContract<PC, VC>,PC: PresenterContract<VC, PC>> : UIViewController {

    @OverrideInit
    constructor(coder: NSCoder) : super(coder = coder)

    abstract val viewAdapter : VC

    external override fun viewWillAppear(animated: Boolean) {
        super.viewWillAppear(animated)
        viewAdapter.presenter.onAttach()
    }

    external override fun viewDidDisappear(animated: Boolean) {
        super.viewDidDisappear(animated)
        viewAdapter.presenter.onDetach()
    }
}
*/
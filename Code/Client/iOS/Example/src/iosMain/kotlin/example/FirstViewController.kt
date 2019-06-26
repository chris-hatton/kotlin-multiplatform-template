
package example

import kotlinx.cinterop.ExportObjCClass
import kotlinx.cinterop.ObjCAction
import kotlinx.cinterop.ObjCOutlet
import platform.Foundation.NSCoder
import platform.UIKit.UIButton
import platform.UIKit.UILabel
import platform.UIKit.UITextField
import platform.UIKit.UIViewController
import example.ui.contract.FirstViewContract
import example.ui.contract.FirstPresenterContract
import example.ui.FirstPresenter

@ExportObjCClass
class FirstViewController : UIViewController {

    @OverrideInit
    constructor(coder: NSCoder) : super(coder = coder)

    @ObjCOutlet
    lateinit var label: UILabel

    @ObjCOutlet
    lateinit var textField: UITextField

    @ObjCOutlet
    lateinit var button: UIButton

    override fun viewDidLoad() {
        super.viewDidLoad()
        println("viewDidLoad")
    }

    @ObjCAction
    fun buttonPressed() {
        viewAdapter.presenter.didSetName(name = textField.text ?: "")
    }

    private val viewAdapter = FirstViewAdapter()

    inner class FirstViewAdapter : BaseViewAdapter<FirstViewAdapter,FirstViewContract,FirstPresenterContract>(), FirstViewContract {
        override fun displayGreeting(text: String) {
            label.text = text
        }

        override val presenter: FirstPresenter by lazy {
            FirstPresenter(
                baseUrl = "http://localhost:8080",
                view    = this
            )
        }
    }

    override fun viewWillAppear(animated: Boolean) {
        super.viewWillAppear(animated)
        viewAdapter.viewWillAppear(animated)
    }

    override fun viewDidDisappear(animated: Boolean) {
        viewAdapter.viewDidDisappear(animated)
        super.viewDidDisappear(animated)
    }
}


package example

import kotlinx.cinterop.ExportObjCClass
import kotlinx.cinterop.ObjCAction
import kotlinx.cinterop.ObjCOutlet
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.MainScope
import org.chrishatton.example.ui.TodoListPresenter
import org.chrishatton.multimvp.ui.Cycleable
import org.chrishatton.multimvp.ui.CycleableMixIn
import org.chrishatton.example.ui.TodoListContract.Presenter as Presenter
import org.chrishatton.example.ui.TodoListContract.View as View
import platform.Foundation.NSCoder
import platform.UIKit.UIButton
import platform.UIKit.UILabel
import platform.UIKit.UITextField
import platform.UIKit.UIViewController

@FlowPreview
@ExperimentalCoroutinesApi
@ExportObjCClass
@InternalCoroutinesApi
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

    private val viewAdapter = TodoListViewAdapter()

    @FlowPreview
    inner class TodoListViewAdapter : BaseViewAdapter<TodoListViewAdapter, View, Presenter>(), View,
        Cycleable by CycleableMixIn(scopeCreator = ::MainScope) {

        override fun displayGreeting(text: String) {
            label.text = text
        }

        override val presenter: TodoListPresenter by lazy {
            println("Heya!")
            TodoListPresenter(
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

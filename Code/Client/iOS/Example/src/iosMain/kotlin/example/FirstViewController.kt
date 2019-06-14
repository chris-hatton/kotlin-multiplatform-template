//
//  FirstViewController.kt
//  Example
//
//  Created by Christopher Hatton on 2019-05-04.
//  Copyright © 2019 Arvis. All rights reserved.
//

package example

import example.ui.FirstPresenter
import example.ui.contract.FirstViewContract
import kotlinx.cinterop.ExportObjCClass
import kotlinx.cinterop.ObjCAction
import kotlinx.cinterop.ObjCOutlet
import platform.Foundation.NSCoder
import platform.UIKit.UIButton
import platform.UIKit.UILabel
import platform.UIKit.UITextField
import platform.UIKit.UIViewController

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
    fun buttonPressed() = viewAdapter.presenter.didSetName(name = textField.text ?: "")

    private val viewAdapter = object : FirstViewContract {
        override fun displayGreeting(text: String) {
            label.text = text
        }

        override val presenter: FirstPresenter by lazy {
            FirstPresenter(
                client          = AppDelegate.instance.client,
                createMainScope = AppDelegate.instance.createMainScope,
                view            = this
            )
        }
    }
}

//
//  ViewController.kt
//  Example
//
//  Created by Christopher Hatton on 2019-05-04.
//  Copyright © 2019 Arvis. All rights reserved.
//

package example

import kotlinx.cinterop.*
import platform.Foundation.*
import platform.UIKit.*

@ExportObjCClass
class ViewController : UIViewController {

    constructor(aDecoder: NSCoder) : super(aDecoder)
    override fun initWithCoder(aDecoder: NSCoder) = initBy(ViewController(aDecoder))

    @ObjCOutlet
    lateinit var label: UILabel

    @ObjCOutlet
    lateinit var textField: UITextField

    @ObjCOutlet
    lateinit var button: UIButton

    @ObjCAction
    fun buttonPressed() {
        label.text = "Konan says: 'Hellooo, ${textField.text}!'"
    }
}

//
//  main.kt
//  Example
//
//  Created by Christopher Hatton on 2019-05-04.
//  Copyright © 2019 Arvis. All rights reserved.
//

import kotlinx.cinterop.*
import platform.Foundation.*
import platform.UIKit.*
import example.*

fun main(args: Array<String>) {
    memScoped {
        val argc = args.size + 1
        val argv = (arrayOf("konan") + args).map { it.cstr.ptr }.toCValues()

        autoreleasepool {
            UIApplicationMain(argc, argv, null, NSStringFromClass(AppDelegate))
        }
    }
}

//
//  AppDelegate.kt
//  Example
//
//  Created by Christopher Hatton on 2019-05-04.
//  Copyright © 2019 Arvis. All rights reserved.
//

package example

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.cinterop.initBy
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.UnstableDefault
import org.chrishatton.example.client
import platform.UIKit.*

class AppDelegate : UIResponder, UIApplicationDelegateProtocol {

    companion object : UIResponderMeta(), UIApplicationDelegateProtocolMeta {
        val instance : AppDelegate get() = UIApplication.sharedApplication.delegate as AppDelegate
    }

    @OverrideInit
    constructor() : super()

    //override fun init() = initBy(AppDelegate())

    private var _window: UIWindow? = null
    override fun window() = _window
    override fun setWindow(window: UIWindow?) { _window = window }

    @UnstableDefault
    @ImplicitReflectionSerializer
    override fun applicationDidFinishLaunching(application: UIApplication) {
        println("Startup")
    }

    override fun applicationWillTerminate(application: UIApplication) {
        client.close()
    }
}

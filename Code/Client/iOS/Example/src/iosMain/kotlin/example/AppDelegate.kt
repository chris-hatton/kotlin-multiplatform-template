//
//  AppDelegate.kt
//  Example
//
//  Created by Christopher Hatton on 2019-05-04.
//  Copyright © 2019 Arvis. All rights reserved.
//

package example

import example.model.Person
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.cinterop.initBy
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.UnstableDefault
import platform.UIKit.*

class AppDelegate : UIResponder(), UIApplicationDelegateProtocol {

    companion object : UIResponderMeta(), UIApplicationDelegateProtocolMeta {
        val instance : AppDelegate get() = UIApplication.sharedApplication.delegate as AppDelegate
    }

    override fun init() = initBy(AppDelegate())

    private var _window: UIWindow? = null
    override fun window() = _window
    override fun setWindow(window: UIWindow?) { _window = window }

    @UnstableDefault
    @ImplicitReflectionSerializer
    override fun applicationDidFinishLaunching(application: UIApplication) {
        println("Startup")
    }

    val client : HttpClient by lazy {
        HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer().apply {
                    setMapper( type = Person::class, serializer = Person.serializer())
                }
            }
        }
    }

    val createMainScope : ()->CoroutineScope = { MainScope() }

    override fun applicationWillTerminate(application: UIApplication) {
        client.close()
    }
}

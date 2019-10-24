
package example

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

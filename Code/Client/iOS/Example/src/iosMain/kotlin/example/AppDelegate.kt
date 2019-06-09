//
//  AppDelegate.kt
//  Example
//
//  Created by Christopher Hatton on 2019-05-04.
//  Copyright © 2019 Arvis. All rights reserved.
//

package example

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.cinterop.*
import kotlinx.coroutines.runBlocking
import platform.UIKit.*
import example.model.Person
import io.ktor.client.features.json.JsonFeature

class AppDelegate : UIResponder(), UIApplicationDelegateProtocol {

    companion object : UIResponderMeta(), UIApplicationDelegateProtocolMeta {}

    override fun init() = initBy(AppDelegate())

    private var _window: UIWindow? = null
    override fun window() = _window
    override fun setWindow(window: UIWindow?) { _window = window }

    override fun applicationDidFinishLaunching(application: UIApplication) {

        println("HI!!!!!!!")

        runBlocking {
            val client = HttpClient {
                install(JsonFeature)
            }

            val message = client.post<Person> { //(path = "/path") {
                url("http://localhost:8080/path")
                contentType(ContentType.Application.Json)
                body = Person("James", "Spatchcock")
            }

            println("CLIENT: Message from the server: $message")

            client.close()
        }
    }
}

package example.common

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.websocket.WebSockets
import io.ktor.client.features.websocket.ws
import io.ktor.http.HttpMethod
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.readText
import kotlinx.coroutines.channels.filterNotNull
import kotlinx.coroutines.channels.map
import kotlinx.coroutines.runBlocking


//actual class TestClient {
//    actual fun test() {
//        runBlocking {
//            val client = HttpClient(CIO).config { install(WebSockets) }
//            client.ws(method = HttpMethod.Get, host = "bm.chrishatton.org", port = 443, path = "api/Server/echo") {
//                send(Frame.Text("Hello World"))
//                for (message in incoming.map { it as? Frame.Text }.filterNotNull()) {
//                    println("Server said: " + message.readText())
//                }
//            }
//        }
//    }
//}
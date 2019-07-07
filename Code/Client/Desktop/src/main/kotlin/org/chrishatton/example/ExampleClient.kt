package org.chrishatton.example

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.websocket.WebSockets

//actual val client : HttpClient = HttpClient(CIO) {
//    install(WebSockets)
//}
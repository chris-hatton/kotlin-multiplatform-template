package example

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.features.*
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.readText
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.serialization.json
import io.ktor.server.netty.EngineMain
import io.ktor.sessions.Sessions
import io.ktor.websocket.webSocket
import org.chrishatton.example.model.Person

fun main(args: Array<String>): Unit = EngineMain.main(args)

/**
 * To run start with Gretty Gradle task 'appStartWar'.
 * To debug start 'appStartWarDebug' and attach debugger on 5005 when execution first pauses.
 */
@KtorExperimentalLocationsAPI
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(CallLogging)

    install(ContentNegotiation) {
        json()
    }

    install(Locations) {
    }

    install(Sessions) {
        //cookie<MySession>("MY_SESSION") {
        //    cookie.extensions["SameSite"] = "lax"
        //}
    }

    install(Compression) {
        gzip {
            priority = 1.0
        }
        deflate {
            priority = 10.0
            minimumSize(1024) // condition
        }
    }

    install(AutoHeadResponse)

    install(DefaultHeaders) {
        header("X-Engine", "Ktor") // will send this header with each response
    }

    install(Authentication) {
    }

    install(io.ktor.websocket.WebSockets) {
//        pingPeriod = Duration.ofSeconds(15)
//        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    routing {
        get("/") {
            call.respondText("HELLO WORLD!")
        }

        webSocket("/server/echo") {
            send(Frame.Text("Hi from server"))
            while (true) {
                val frame = incoming.receive()
                if (frame is Frame.Text) {
                    send(Frame.Text("Client said: " + frame.readText()))
                }
            }
        }

        post<Person>("/person") { person:Person ->
            val acquaintedPerson = Person("Mark", "Halliwell")
            println("I think ${person.fullName} might know ${acquaintedPerson.fullName}")
            call.respond(acquaintedPerson)
        }
    }
}

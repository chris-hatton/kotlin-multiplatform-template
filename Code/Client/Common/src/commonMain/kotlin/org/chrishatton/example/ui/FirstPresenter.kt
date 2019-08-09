package org.chrishatton.example.ui

import org.chrishatton.example.client
import org.chrishatton.example.model.Person
import org.chrishatton.example.netScope
import org.chrishatton.example.uiScope
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import org.chrishatton.example.ui.contract.FirstContract

@kotlinx.coroutines.InternalCoroutinesApi
@kotlinx.coroutines.ExperimentalCoroutinesApi
class FirstPresenter(
        val baseUrl : String,
        override val view: FirstContract.View
) : FirstContract.Presenter {
    private lateinit var setNameChannel : Channel<String>
    private lateinit var peopleChannel  : Channel<Pair<Person, Person>>

    override fun onAttach() {

        setNameChannel = Channel()
        peopleChannel  = Channel()

        netScope.launch {
            for (name in setNameChannel) {
                val person = Person(names = name.split(" "))
                val otherPerson = try {
                    println("A")
                    val returnPerson = client.post<Person> {
                        url("$baseUrl/person")
                        contentType(ContentType.Application.Json)
                        body = person
                    }
                    println("B")
                    returnPerson
                } catch(e: Exception) {
                    println("Exception: $e, cause: ${e.cause}")
                    Person("Nigel", "Ernest", "Body")
                }
                peopleChannel.send(person to otherPerson)
            }
        }

        uiScope.launch {
            for((person,otherPerson) in peopleChannel) {
                view.displayGreeting(text = "Hello ${person.firstName}, do you know ${otherPerson.fullName}?")
            }
        }
    }

    override fun onDetach() {
        setNameChannel.close()
        peopleChannel.close()
    }

    override fun didSetName(name: String) {
        uiScope.launch {
            //println("Hi")
            setNameChannel.send(name)
        }
    }
}

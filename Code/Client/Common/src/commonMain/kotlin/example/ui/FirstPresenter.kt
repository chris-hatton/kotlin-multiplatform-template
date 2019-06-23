package example.ui

import example.client
import example.model.Person
import example.netScope
import example.ui.contract.FirstPresenterContract
import example.ui.contract.FirstViewContract
import example.uiScope
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class FirstPresenter(
        override val view: FirstViewContract
) : FirstPresenterContract {

    private lateinit var setNameChannel : Channel<String>
    private lateinit var peopleChannel  : Channel<Pair<Person, Person>>

    override fun onAttach() {

        setNameChannel = Channel()
        peopleChannel  = Channel()

        netScope.launch {
            for (name in setNameChannel) {
                val person = Person(names = name.split(" "))
                val otherPerson = try {
                    client.post<Person> {
                        url("http://localhost:8080/person")
                        contentType(ContentType.Application.Json)
                        body = person
                    }
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
            setNameChannel.send(name)
        }
    }
}

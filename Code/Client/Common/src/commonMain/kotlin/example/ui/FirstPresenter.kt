package example.ui

import example.model.Person
import example.ui.contract.FirstPresenterContract
import example.ui.contract.FirstViewContract
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class FirstPresenter(
        val client: HttpClient,
        val createMainScope : ()->CoroutineScope,
        override val view: FirstViewContract
) : FirstPresenterContract {
    override fun didSetName(name: String) {

        createMainScope().launch {

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

            view.displayGreeting(text = "Hello ${person.firstName}, do you know ${otherPerson.fullName}?")
        }
    }
}

package org.chrishatton.example

import org.chrishatton.example.model.Person
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer

actual val client : HttpClient by lazy {
    HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer().apply {
                //setMapper( type = Person::class, serializer = Person.serializer())
            }
        }
    }
}

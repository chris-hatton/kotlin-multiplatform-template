package example.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
public data class Person(
    val names: List<String>
) {
    constructor() : this(emptyList())

    constructor(vararg names: String) : this(names.toList())

    @Transient
    @kotlin.jvm.Transient
    val firstName : String? = names.firstOrNull()

    @Transient
    @kotlin.jvm.Transient
    val lastName  : String? = if(names.size<2) null else names.last()

    @Transient
    @kotlin.jvm.Transient
    val firstLastName : String? = listOf(firstName,lastName).joinToString(" ")

    @Transient
    @kotlin.jvm.Transient
    val fullName : String? = names.joinToString(" ")
}

package example.model

data class Person(
    val names: List<String>
) {
    val firstName : String? = names.firstOrNull()
    val lastName  : String? = if(names.size<2) null else names.last()

    val firstLastName : String? = listOf(firstName,lastName).joinToString(" ")
    val fullName : String? = names.joinToString(" ")
}

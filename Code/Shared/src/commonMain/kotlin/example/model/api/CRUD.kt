package example.model.api

import example.model.UUID

sealed class CRUD<T> {
    data class Create<T>  (val `object`: T)    : CRUD<T>()
    data class Update<T>  (val `object`: T)    : CRUD<T>()
    data class Retrieve<T>(val objectId: UUID) : CRUD<T>()
    data class Delete<T>  (val objectId: UUID) : CRUD<T>()
}

package example

import io.ktor.sessions.SessionStorage
import kotlinx.coroutines.io.ByteReadChannel
import kotlinx.coroutines.io.ByteWriteChannel
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteWhere

object KtorSessions : Table() {
    val id   = varchar("id", 10).primaryKey() // Column<String>
    val data = varchar("name", length = 1024) // Column<String>
}

interface ExposedSessionStorage : SessionStorage {

    override suspend fun invalidate(id: String) {
        KtorSessions.deleteWhere { KtorSessions.id == id }
    }

    override suspend fun <R> read(id: String, consumer: suspend (ByteReadChannel) -> R): R {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun write(id: String, provider: suspend (ByteWriteChannel) -> Unit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
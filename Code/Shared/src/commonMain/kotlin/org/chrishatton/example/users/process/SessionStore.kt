package org.chrishatton.example.users.process

import org.chrishatton.example.users.model.Session
import org.chrishatton.example.users.model.User

interface SessionStore {
    fun activeSessionOrNull(user: User) : Session?
    fun sessionActivated(session: Session)
}
package org.chrishatton.example.model

sealed class Validated<T>(val value: T) {
    class Valid<T>(value: T) : Validated<T>(value)
    class Invalid<T>(value: T, val reasons: List<String> = emptyList()) : Validated<T>(value) {
        constructor(value: T, reason: String) : this(value,listOf(reason))
    }
}

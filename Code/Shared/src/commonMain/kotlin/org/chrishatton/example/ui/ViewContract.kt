package org.chrishatton.example.ui

interface ViewContract<P: PresenterContract<Self, P>,Self: ViewContract<P, Self>> {
    val presenter : P
}

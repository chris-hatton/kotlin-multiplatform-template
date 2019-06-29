package org.chrishatton.example.ui.contract

import org.chrishatton.example.ui.ViewContract

interface FirstViewContract : ViewContract<FirstPresenterContract, FirstViewContract> {
    fun displayGreeting(text: String)
}
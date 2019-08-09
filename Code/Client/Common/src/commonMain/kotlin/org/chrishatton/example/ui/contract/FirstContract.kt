package org.chrishatton.example.ui.contract

interface FirstContract {
    interface Presenter : FirstContract, Contract.Presenter<View, Presenter> {
        fun didSetName(name: String)
    }

    interface View : FirstContract, Contract.View<Presenter, View> {
        fun displayGreeting(text: String)
    }
}
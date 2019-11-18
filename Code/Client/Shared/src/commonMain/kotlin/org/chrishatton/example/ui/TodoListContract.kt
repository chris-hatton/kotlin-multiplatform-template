package org.chrishatton.example.ui

import org.chrishatton.multimvp.ui.Contract

interface TodoListContract : Contract {
    interface Presenter : Contract.Presenter<View, Presenter>, TodoListContract {
        fun didSetName(name: String)
    }

    interface View : Contract.View<Presenter, View>, TodoListContract {
        fun displayGreeting(text: String)
    }
}

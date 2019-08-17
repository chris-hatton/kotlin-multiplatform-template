package org.chrishatton.example.ui

import org.chrishatton.multimvp.ui.Contract

interface FirstContract : Contract {
    interface Presenter : Contract.Presenter<View, Presenter>, FirstContract {
        fun didSetName(name: String)
    }

    interface View : Contract.View<Presenter, View>, FirstContract {
        fun displayGreeting(text: String)
    }
}

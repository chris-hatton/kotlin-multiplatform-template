package org.chrishatton.example.ui.contract

interface Contract {

    interface View<P: Presenter<Self, P>,Self: View<P, Self>> : Contract {
        val presenter : P
    }

    interface Presenter<V: View<Self, V>,Self: Presenter<V, Self>> : Contract {
        val view : V

        fun onAttach()
        fun onDetach()
    }

}

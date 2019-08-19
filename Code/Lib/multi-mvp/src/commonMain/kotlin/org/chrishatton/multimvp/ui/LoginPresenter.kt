package org.chrishatton.example.ui

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.chrishatton.example.ui.contract.LoginContract

@FlowPreview
@ExperimentalCoroutinesApi
class LoginPresenter(override val view: LoginContract.View) : LoginContract.Presenter {

    override fun onAttach() {

    }

    override fun onDetach() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
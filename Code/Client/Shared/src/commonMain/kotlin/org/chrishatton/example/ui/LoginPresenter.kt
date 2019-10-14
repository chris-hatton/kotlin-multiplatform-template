package org.chrishatton.example.ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.chrishatton.example.ui.contract.LoginContract
import org.chrishatton.multimvp.ui.BasePresenter

@FlowPreview
@ExperimentalCoroutinesApi
class LoginPresenter(override val view: LoginContract.View) : BasePresenter<LoginContract.View,LoginContract.Presenter>(view), LoginContract.Presenter {

    override fun start() {
    }

    override fun stop() {

    }
}
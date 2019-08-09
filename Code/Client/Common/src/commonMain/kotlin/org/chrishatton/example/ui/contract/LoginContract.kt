package org.chrishatton.example.ui.contract

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import org.chrishatton.example.model.Validated

@FlowPreview
@ExperimentalCoroutinesApi
interface LoginContract {
    interface Presenter : LoginContract, Contract.Presenter<View, Presenter> {

    }

    interface View : LoginContract, Contract.View<Presenter, View> {

        val validatedUsernameCollector        : FlowCollector<Validated<String>>
        val validatedPasswordCollector        : FlowCollector<Validated<String>>
        val validatedConfirmPasswordCollector : FlowCollector<Validated<String>>

        val loginUsernames : Flow<String>
        val loginPasswords : Flow<String>

        val registerUsername        : Flow<String>
        val registerPassword        : Flow<String>
        val registerConfirmPassword : Flow<String>

        val registrationSubmits : BroadcastChannel<Unit>
        val loginSubmits        : BroadcastChannel<Unit>
    }
}
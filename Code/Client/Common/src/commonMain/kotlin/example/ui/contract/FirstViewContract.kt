package example.ui.contract

import example.ui.ViewContract

interface FirstViewContract : ViewContract<FirstPresenterContract,FirstViewContract> {
    fun displayGreeting(text: String)
}
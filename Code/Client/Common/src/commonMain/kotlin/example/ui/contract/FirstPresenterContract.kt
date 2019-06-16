package example.ui.contract

import example.ui.PresenterContract

interface FirstPresenterContract : PresenterContract<FirstViewContract,FirstPresenterContract> {
    fun didSetName(name: String)
}

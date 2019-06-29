package org.chrishatton.example.ui.contract

import org.chrishatton.example.ui.PresenterContract

interface FirstPresenterContract :
    PresenterContract<FirstViewContract, FirstPresenterContract> {

    fun didSetName(name: String)

}

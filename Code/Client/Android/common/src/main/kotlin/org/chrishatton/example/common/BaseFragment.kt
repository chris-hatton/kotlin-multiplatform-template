package org.chrishatton.example.common

import android.content.Context
import androidx.fragment.app.Fragment
import org.chrishatton.example.ui.PresenterContract
import org.chrishatton.example.ui.ViewContract

/**
 * Base for Android Views (as Fragments) which follow the Multiplatorm-template's MVP convention:
 * - Implements a ViewContract
 * - Provides a PresenterContract implementation (a Presenter), corresponding 1:1 to the ViewContract
 * - Issues lifecycle callbacks to the Presenter
 */
abstract class BaseFragment<VC: ViewContract<PC, VC>,PC: PresenterContract<VC, PC>> : Fragment(),
    ViewContract<PC, VC> {

    override fun onAttach(activity: Context?) {
        super.onAttach(activity)
        presenter.onAttach()
    }

    override fun onDetach() {
        super.onDetach()
        presenter.onDetach()
    }

}
package example.common

import android.content.Context
import androidx.fragment.app.Fragment
import example.ui.PresenterContract
import example.ui.ViewContract

abstract class BaseFragment<VC:ViewContract<PC,VC>,PC:PresenterContract<VC,PC>> : Fragment(), ViewContract<PC, VC> {

    override fun onAttach(activity: Context?) {
        super.onAttach(activity)
        presenter.onAttach()
    }

    override fun onDetach() {
        super.onDetach()
        presenter.onDetach()
    }

}
package example.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import example.ui.FirstPresenter
import example.ui.contract.FirstViewContract
import kotlinx.android.synthetic.main.fragment_first.responseText
import kotlinx.android.synthetic.main.fragment_first.sendButton
import kotlinx.android.synthetic.main.fragment_first.nameEntry
import kotlinx.android.synthetic.main.fragment_first.view.*

class FirstFragment : Fragment(), FirstViewContract {

    override fun displayGreeting(text: String) {
        responseText.text = text
    }

    override val presenter : FirstPresenter by lazy {
        FirstPresenter(
            client = Example.httpClient,
            createMainScope = { MainScope() },
            view = this
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false).also { view ->
            view.sendButton.setOnClickListener {
                presenter.didSetName(nameEntry.text.toString())
            }
        }
    }


}

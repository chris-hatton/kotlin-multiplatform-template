package org.chrishatton.example.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_first.view.*
import kotlinx.coroutines.InternalCoroutinesApi
import org.chrishatton.example.ui.FirstContract
import org.chrishatton.example.ui.FirstPresenter

@InternalCoroutinesApi
@kotlinx.coroutines.ExperimentalCoroutinesApi
class FirstFragment : BaseFragment<FirstContract.View, FirstContract.Presenter>(),
    FirstContract.View {

    override fun displayGreeting(text: String) {
        responseText.text = text
    }

    override val presenter : FirstPresenter by lazy {
        FirstPresenter(
            baseUrl = "http://10.0.2.2:8080",
            view = this
        )
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

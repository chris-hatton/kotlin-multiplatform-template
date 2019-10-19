package org.chrishatton.multimvp.ui

import androidx.fragment.app.Fragment
import kotlinx.coroutines.*

/**
 * Implementation of a base View for our MVP framework, as backed by an Android Fragment.
 */
@ExperimentalCoroutinesApi
abstract class BaseViewFragment<P:Contract.Presenter<V,P>,V:Contract.View<P,V>>
    : Fragment(), Contract.View<P,V> {

    override var lifecycleScope : CoroutineScope? = null

    private object LifecycleLock

    /**
     * This is an Android Fragment life-cycle callback.
     * We allow this to drive the lifecycle of our MVP framework too; setting up the job & context
     * of this [CoroutineScope]passing `start` to the Views Presenter.
     */
    override fun onStart() {
        synchronized(LifecycleLock) {
            require(lifecycleScope==null)
            super.onStart()
            lifecycleScope = CoroutineScope(Dispatchers.Main)
            presenter.start()
        }
    }

    /**
     * This is an Android Fragment life-cycle callback.
     * We allow this to drive the lifecycle of our MVP framework too; passing `stop` to the Views
     * Presenter and cancelling the job & context of this [CoroutineScope].
     */
    override fun onStop() {
        synchronized(LifecycleLock) {
            require(lifecycleScope!=null)
            presenter.stop()
            lifecycleScope?.cancel()
            lifecycleScope = null
            super.onStop()
        }
    }
}

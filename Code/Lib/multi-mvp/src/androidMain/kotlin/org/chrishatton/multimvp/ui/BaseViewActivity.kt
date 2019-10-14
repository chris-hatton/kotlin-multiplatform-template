package org.chrishatton.multimvp.ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.cancel

/**
 * A base View on top of PreCom's 'WorkflowScanScreenBase'.
 * This satisfies the MVP contract, providing a UI Dispatcher co-routine scope.
 */
abstract class BaseViewActivity<P:Contract.Presenter<V,P>,V:Contract.View<P,V>>
    : AppCompatActivity(), CoroutineScope, Contract.View<P,V> {

    /**
     * According to our MVP framework convention, View implementations must
     * instantiate their Presenter; typically by implementing a lazy-initialized var.
     */
    abstract override val presenter: P

    override var lifecycleScope : CoroutineScope? = null

    private object LifecycleLock

    /**
     * This is an Android Activity life-cycle callback.
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
     * This is an Android Activity life-cycle callback.
     * We allow this to drive the lifecycle of our MVP framework too; passing `stop` to the Views
     * Presenter and cancelling the job & context of this [CoroutineScope].
     */
    override fun onStop() {
        synchronized(LifecycleLock) {
            require(lifecycleScope!=null)
            presenter.stop()
            lifecycleScope?.cancel()
            super.onStop()
        }
    }
}

package org.chrishatton.multimvp.ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * Implementation of a base View for our MVP framework, as backed by an Android Fragment.
 */
@ExperimentalCoroutinesApi
abstract class BaseViewFragment<P:Contract.Presenter<V,P>,V:Contract.View<P,V>>
    : Fragment(), CoroutineScope, Contract.View<P,V> {

    /**
     * According to our MVP framework convention, View implementations must
     * instantiate their Presenter; typically by implementing a lazy-initialized var.
     */
    abstract override val presenter: P

    private var _coroutineContext : CoroutineContext? = null
    private var _job : Job? = null

    final override val coroutineContext: CoroutineContext
        get() = _coroutineContext ?: throw IllegalStateException("Attempt to access coroutineContext while this Fragment is detached")

    /**
     * This is an Android Fragment life-cycle callback.
     * We allow this to drive the lifecycle of our MVP framework too; setting up the job & context
     * of this [CoroutineScope]passing `start` to the Views Presenter.
     */
    override fun onStart() {
        super.onStart()
        val job = Job().also { _job = it }
        _coroutineContext = Dispatchers.Main + job
        presenter.start()
    }

    /**
     * This is an Android Fragment life-cycle callback.
     * We allow this to drive the lifecycle of our MVP framework too; passing `stop` to the Views
     * Presenter and cancelling the job & context of this [CoroutineScope].
     */
    override fun onStop() {
        presenter.stop()
        _job!!.cancel()
        _job = null
        super.onStop()
    }
}

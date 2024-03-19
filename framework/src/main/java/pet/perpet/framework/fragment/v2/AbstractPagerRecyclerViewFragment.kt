package pet.perpet.framework.fragment.v2

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import pet.perpet.framework.fragment.Fragment
import pet.perpet.framework.nonnull
import pet.perpet.framework.util.ViewUtil
import pet.perpet.framework.widget.recyclerview.AdapterUtil
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewAdapter
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder
import pet.perpet.framework.widget.recyclerview.OnViewHolderEventListener
import pet.perpet.framework.widget.recyclerview.RecyclerViewAdapter

abstract class AbstractPagerRecyclerViewFragment<P: PagerRecyclerViewPresenterV1> :
    Fragment<P>(),
    OnViewHolderEventListener
{
    //======================================================================
    // Private Methods
    //======================================================================

    protected abstract val recyclerView: RecyclerView

    val layoutManager: RecyclerView.LayoutManager?
        get() = recyclerView.layoutManager

    val isEmptyHolder: Boolean
        get() = adapter.supportItemCount <= 0

    open val dependencyEmptyView: View?
        get() = null

    val adapter: BaseRecyclerViewAdapter
        get() {
            return recyclerView.adapter as BaseRecyclerViewAdapter
        }

    open val isValidRequestParameter
        get() = true

    //======================================================================
    // Abstract Methods
    //======================================================================

    abstract fun onCreateLayoutManager(): RecyclerView.LayoutManager

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel.pageCompleteNotify?.observe(this) {
            complete()
            onPageRequestComplete()
        }
        viewmodel.pageStartNotify?.observe(this) {
            if (it == PagerRecyclerViewPresenterV1.PagerStartStatus.FULL) {
                onPageRequestStart()
            }
        }
        viewmodel.notifyItemChange?.observe(this) {
            if(it == true && layoutManager?.canScrollVertically() == true ) {
                //recyclerView.setSwipeRefreshLayoutEnable(true)
            } else {
                //recyclerView.setSwipeRefreshLayoutEnable(false)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setSwipeRefreshLayoutEnable(false)
        /*recyclerView.recyclerView.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                if (e.action == MotionEvent.ACTION_DOWN) {
                    when (rv.scrollState) {
                        RecyclerView.SCROLL_STATE_SETTLING, RecyclerView.SCROLL_STATE_DRAGGING -> rv.stopScroll()
                    }
                }
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

            }
        })*/

        val adapter = viewmodel.adapter
        adapter.setHolderEventListener(this)
        recyclerView.adapter = adapter

        /* val processor = onCreateFooterViewHolderProcessor()
         if (processor != null) {
             adapter.setFooterViewHolderProcessor(processor)
         }

         onCreateEmptyViewHolderProcessor()?.let {
             adapter.setEmptyViewHolderEnable(true)
             adapter.setEmptyViewHolderProcessor(it)
         }*/

        recyclerView.adapter = adapter
        recyclerView.layoutManager = onCreateLayoutManager()
        /*recyclerView.swipeRefreshLayout.setOnRefreshListener {
            onRefresh()
        }
        recyclerView.setSwipeRefreshLayoutEnable(false)*/
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        val itemUse = viewmodel.adapter.supportItemCount.nonnull() > 0

        fun notify() {
            if (isEmptyList() == true) {
                onShowEmptyView()
            }
            notifySupportDataSetChanged()
        }

        if (itemUse == false) {
            if (viewmodel.config.firstStatPageRequest == true) {
                executeInternal(PagerRecyclerViewPresenterV1.PagerStartStatus.FULL)
            } else {
                notify()
            }
        } else {
            notify()
        }
    }

    open fun onRefresh() {
        executeInternal(PagerRecyclerViewPresenterV1.PagerStartStatus.TOP)
    }

    override fun onRecyclerViewHolderEvent(
        holder: BaseRecyclerViewHolder<*>,
        id: Int,
        bundle: Bundle
    ) {

    }

    //======================================================================
    // Protected Methods
    //======================================================================

    /*protected fun setSwipeRefreshLayoutEnable(enable: Boolean) {
        recyclerView.setSwipeRefreshLayoutEnable(enable)
    }*/

    protected fun notifyItemRemoved(position: Int) {
        AdapterUtil.notifyItemRemoved(recyclerView, position)
    }

    protected fun notifyItemChanged(position: Int) {
        AdapterUtil.notifyItemChanged(recyclerView, position)
    }

    protected fun notifySupportDataSetChanged() {
        AdapterUtil.notifySupportDataSetChanged(recyclerView.adapter as RecyclerViewAdapter, true)
    }

    //======================================================================
    // Public Methods
    //======================================================================

    open fun onShowEmptyView() {
        ViewUtil.show(dependencyEmptyView)
    }

    open fun onPageRequestComplete() {
        if (isEmptyHolder == true) {
            onShowEmptyView()
        } else {
            ViewUtil.hide(dependencyEmptyView)
        }
    }

    open fun onPageRequestStart() {

    }

    fun execute() {
        executeInternal(PagerRecyclerViewPresenterV1.PagerStartStatus.FULL)
    }

    //======================================================================
    // Variables
    //======================================================================

    private fun executeInternal(pagerStartStatus: PagerRecyclerViewPresenterV1.PagerStartStatus) {
        try {
            viewmodel.execute(pagerStartStatus)
        } catch (e: Exception) {
            e.printStackTrace()
            complete()
            onPageRequestComplete()
        }
    }

    private fun isEmptyList(): Boolean {
        return adapter.supportItemCount <= 0
    }

    private fun complete() {
        try {
            //recyclerView.setRefreshing(false)
        } catch (e: Exception) {
            // Nothing
        }
    }
}
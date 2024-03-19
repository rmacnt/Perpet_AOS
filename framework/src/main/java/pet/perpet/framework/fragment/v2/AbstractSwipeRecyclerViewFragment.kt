package pet.perpet.framework.fragment.v2

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import pet.perpet.framework.fragment.Fragment
import pet.perpet.framework.fragment.RecyclerViewPresenter
import pet.perpet.framework.fragment.ViewHolderFactory
import pet.perpet.framework.widget.recyclerview.AdapterUtil
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewAdapter
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder
import pet.perpet.framework.widget.recyclerview.OnViewHolderEventListener
import pet.perpet.framework.widget.recyclerview.RecyclerViewHolder
import pet.perpet.framework.widget.recyclerview.SwipeRefreshRecyclerView
import pet.perpet.framework.widget.recyclerview.scroll.ScrollController

abstract class AbstractSwipeRecyclerViewFragment<P : RecyclerViewPresenter> : Fragment<P>(),
    SwipeRefreshLayout.OnRefreshListener, OnViewHolderEventListener {

    //======================================================================
    // Variables
    //======================================================================

    protected abstract val recyclerView: SwipeRefreshRecyclerView

    //======================================================================
    // Public Methods
    //======================================================================

    val layoutManager: RecyclerView.LayoutManager?
        get() = recyclerView.layoutManager

    val adapter: BaseRecyclerViewAdapter
        get() {
            return recyclerView.adapter as BaseRecyclerViewAdapter
        }

    //======================================================================
    // Abstract Methods
    //======================================================================

    abstract fun onCreateLayoutManager(): RecyclerView.LayoutManager

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSwipeRefreshLayoutEnable(false)
        recyclerView.recyclerView.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
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
        })

        val adapter = viewmodel.adapter
        adapter.setHolderEventListener(this)

        val processor = onCreateFooterViewHolderProcessor()
        if (processor != null) {
            adapter.setFooterViewHolderProcessor(processor)
        }

        onCreateEmptyViewHolderProcessor()?.let {
            adapter.setEmptyViewHolderEnable(true)
            adapter.setEmptyViewHolderProcessor(it)
        }

        recyclerView.adapter = adapter
        recyclerView.recyclerView.itemAnimator = null
        recyclerView.layoutManager = onCreateLayoutManager()
        recyclerView.recyclerView.addOnScrollListener(object : ScrollController() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                this@AbstractSwipeRecyclerViewFragment.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollState(state: State) {
                this@AbstractSwipeRecyclerViewFragment.onScrollState(state)
            }
        })
        recyclerView.swipeRefreshLayout.setOnRefreshListener {
            onRefresh()
        }
        recyclerView.setSwipeRefreshLayoutEnable(false)
    }

    override fun onRefresh() {
        // Override
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

    protected fun setSwipeRefreshLayoutEnable(enable: Boolean) {
        recyclerView.setSwipeRefreshLayoutEnable(enable)
    }

    protected fun notifyItemRemoved(position: Int) {
        AdapterUtil.notifyItemRemoved(recyclerView, position)
    }

    protected fun notifyItemChanged(position: Int) {
        AdapterUtil.notifyItemChanged(recyclerView, position)
    }

    protected open fun onCreateFooterViewHolderProcessor(): BaseRecyclerViewAdapter.FooterViewHolderProcessor? {
        return null
    }

    protected open fun onCreateEmptyViewHolderProcessor(): BaseRecyclerViewAdapter.EmptyViewHolderProcessor? {
        return null
    }

    //======================================================================
    // Public Methods
    //======================================================================

    open fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {

    }

    open fun onScrollState(state: ScrollController.State) {
        //override
    }

    open fun onCreateRecyclerViewAdapter(): BaseRecyclerViewAdapter {
        return object : BaseRecyclerViewAdapter() {

            override fun onCreateItemViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): RecyclerViewHolder<*>? {
                try {
                    val itemClass = presenter.viewHolderSet.findViewHolderClass(viewType)
                    return ViewHolderFactory.create(
                        itemClass,
                        parent,
                        presenter.multiSelector
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return null
            }
        }.apply {
            setHasStableIds(true)
        }
    }

    fun notifySupportDataSetChanged() {
        recyclerView.recyclerView.isNestedScrollingEnabled = false
        recyclerView.recyclerView.recycledViewPool.clear()
        AdapterUtil.notifySupportDataSetChanged(recyclerView.adapter, true)
        recyclerView.setRefreshing(false)
        recyclerView.recyclerView.isNestedScrollingEnabled = true
    }

    //======================================================================
    // Private Methods
    //======================================================================

    private fun releaseView(rootView: View?) {
        if (rootView == null) {
            return
        }

        if (rootView is ViewGroup) {
            val groupView = rootView as ViewGroup?

            val childCount = groupView!!.childCount
            for (index in 0 until childCount) {
                releaseView(groupView.getChildAt(index))
            }
        }
        /*else if (rootView is BaseImageView) {
            AppLogger.i(Tag.VIEW_HOLDER, "release [" + rootView.id + "]")
            val imageView = rootView as BaseImageView?
            if (imageView!!.isUrlTag == true) {
                imageView.releaseImage()
            }
        }*/
    }
}

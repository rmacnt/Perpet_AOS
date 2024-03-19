package pet.perpet.framework.fragment.v2

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pet.perpet.framework.fragment.Fragment
import pet.perpet.framework.fragment.RecyclerViewPresenter
import pet.perpet.framework.fragment.ViewHolderFactory
import pet.perpet.framework.widget.recyclerview.AdapterUtil
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewAdapter
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder
import pet.perpet.framework.widget.recyclerview.OnViewHolderEventListener
import pet.perpet.framework.widget.recyclerview.RecyclerViewAdapter
import pet.perpet.framework.widget.recyclerview.RecyclerViewHolder
import pet.perpet.framework.widget.recyclerview.scroll.ScrollController

abstract class AbstractRecyclerViewFragment<P : RecyclerViewPresenter> : Fragment<P>(),
    OnViewHolderEventListener {

    //======================================================================
    // Variables
    //======================================================================

    protected abstract val recyclerView: RecyclerView

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
        recyclerView.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
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

        val adapter = onCreateRecyclerViewAdapter()
        adapter.setHolderEventListener(this)
        adapter.setHasStableIds(true)
        adapter.setProvider(presenter.adapterProvider)

        val processor = onCreateFooterViewHolderProcessor()
        if (processor != null) {
            adapter.setFooterViewHolderProcessor(processor)
        }

        onCreateEmptyViewHolderProcessor()?.let {
            adapter.setEmptyViewHolderEnable(true)
            adapter.setEmptyViewHolderProcessor(it)
        }

        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = onCreateLayoutManager()
        recyclerView.addOnScrollListener(object : ScrollController() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                this@AbstractRecyclerViewFragment.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollState(state: State) {
                this@AbstractRecyclerViewFragment.onScrollState(state)
            }
        })
    }

    override fun onDestroyView() {
        /*RecyclerViewUtil.releaseRecyclerView(recyclerView)*/
        super.onDestroyView()
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

    protected fun notifyItemRemoved(position: Int) {
        AdapterUtil.notifyItemRemoved(recyclerView, position)
    }

    protected fun notifyItemChanged(position: Int) {
        AdapterUtil.notifyItemChanged(recyclerView, position)
    }

    protected fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {

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
        }
    }

    fun notifySupportDataSetChanged() {
        AdapterUtil.notifySupportDataSetChanged(recyclerView.adapter as RecyclerViewAdapter?, true)
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

    companion object {

        //======================================================================
        // Constants
        //======================================================================

        private const val MIN_SMOOTH_COUNT = 60
    }
}

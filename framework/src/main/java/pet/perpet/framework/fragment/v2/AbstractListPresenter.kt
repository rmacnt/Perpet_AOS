package pet.perpet.framework.fragment.v2

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pet.perpet.framework.fragment.UseViewModel
import pet.perpet.framework.fragment.ViewHolderFactory
import pet.perpet.framework.fragment.ViewHolderSet
import pet.perpet.framework.widget.recyclerview.BaseListAdapter
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewAdapter
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder
import pet.perpet.framework.widget.recyclerview.OnViewHolderEventListener
import pet.perpet.framework.widget.recyclerview.RecyclerViewHolder
import pet.perpet.framework.widget.selector.MultiSelector

abstract class AbstractListPresenter(fragment: Fragment) : UseViewModel(fragment),
    OnViewHolderEventListener {

    //======================================================================
    // Variables
    //======================================================================

    var viewHolderSet: ViewHolderSet =
        ViewHolderSet.EMPTY
        get() {
            if (field == ViewHolderSet.EMPTY) {
                val temp = onCreateViewHolderSet()
                if (temp != null) {
                    viewHolderSet = temp
                }
            }
            return field
        }
        private set

    val adapter: BaseRecyclerViewAdapter by lazy {
        onCreateRecyclerViewAdapter()
    }

    open val multiSelector: MultiSelector?
        get() = null

    //======================================================================
    // Abstract Methods
    //======================================================================

    abstract fun onCreateLayoutManager(): RecyclerView.LayoutManager

    protected abstract fun onCreateViewHolderSet(): ViewHolderSet?

    protected abstract fun onCreateProvider(): BaseListAdapter.ListProvider

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRecyclerViewHolderEvent(
        holder: BaseRecyclerViewHolder<*>,
        id: Int,
        bundle: Bundle
    ) {
        Log.d("onRecycler", "onRecyclerViewHolderEvent")
    }

    //======================================================================
    // Public Methods
    //======================================================================

    fun getRawItem(position: Int): Any? {
        if (adapter is BaseListAdapter) {
            return (adapter as BaseListAdapter).getRawItem(position)
        }
        return null
    }

    fun getRawItemSize(): Int {
        if (adapter is BaseListAdapter) {
            return (adapter as BaseListAdapter).getRawItemSize()
        }
        return 0
    }

    fun submitList(value: List<Any>?) {
        if (adapter is BaseListAdapter) {
            (adapter as BaseListAdapter).submitList(value)
        }
    }

    open fun onAttachRecyclerView(recyclerView: RecyclerView) {
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
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = onCreateLayoutManager()
    }

    //======================================================================
    // Protected Methods
    //======================================================================

    protected fun onCreateFooterViewHolderProcessor(): BaseRecyclerViewAdapter.FooterViewHolderProcessor? {
        return null
    }

    protected fun onCreateEmptyViewHolderProcessor(): BaseRecyclerViewAdapter.EmptyViewHolderProcessor? {
        return null
    }

    protected fun onCrateDiffItemCallback(): DiffUtil.ItemCallback<Any> {
        return object : DiffUtil.ItemCallback<Any>() {
            override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
                return oldItem == newItem
            }
        }
    }

    //======================================================================
    // Private Methods
    //======================================================================

    private fun onCreateRecyclerViewAdapter(): BaseRecyclerViewAdapter {
        val a = object : BaseListAdapter() {
            override fun onCreateListProvider(): ListProvider {
                return this@AbstractListPresenter.onCreateProvider()
            }

            override fun onCreateDiffCallback(): DiffUtil.ItemCallback<Any> {
                return this@AbstractListPresenter.onCrateDiffItemCallback()
            }

            override fun onCreateItemViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): RecyclerViewHolder<*>? {
                try {
                    val itemClass = viewHolderSet.findViewHolderClass(viewType)
                    return ViewHolderFactory.create(
                        itemClass,
                        parent,
                        multiSelector
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return null
            }
        }
        a.setHasStableIds(true)
        a.setHolderEventListener(this)

        onCreateFooterViewHolderProcessor()?.run {
            a.setFooterViewHolderProcessor(this)
        }

        onCreateEmptyViewHolderProcessor()?.let {
            a.setEmptyViewHolderEnable(true)
            a.setEmptyViewHolderProcessor(it)
        }
        return a
    }
}

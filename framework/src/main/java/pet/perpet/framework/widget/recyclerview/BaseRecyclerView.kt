package pet.perpet.framework.widget.recyclerview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import pet.perpet.framework.R

class BaseRecyclerView : RecyclerView {

    //======================================================================
    // Variables
    //======================================================================

    private var provider: Provider? = null

    private var layoutMediator: LayoutMediator? = null

    var disableTouchEvent: Boolean = false

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : this(
        context,
        attrs,
        R.attr.recyclerviewStyle
    )

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    //======================================================================
    // Override Methods
    //======================================================================

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (disableTouchEvent) {
            return false
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun canScrollVertically(direction: Int): Boolean {
        return super.canScrollVertically(direction)
    }

    //======================================================================
    // Public Methods
    //======================================================================

    fun setDecoration(value: Provider) {
        value.view = { this }
        val update = provider != value
        if (update) {
            value.onBindInternal(this)
            adapter = value.adapter
            layoutManager = value.onCreateLayoutManager()
            value.itemDecoration?.let {
                addItemDecoration(it)
            }
            value.snapper?.attachToRecyclerView(this)
        } else {
            value.onBindInternal(this)
        }
        value.onDataSetChange(this, update)
        value.visibleUpdate = {
            visibility = it
        }
        value.scrollPosition = { position, smooth ->
            if (smooth) {
                smoothScrollToPosition(position)
            } else {
                scrollToPosition(position)
            }
        }
        provider = value
    }

    fun setLayoutMediator(value: LayoutMediator) {
        if (value != layoutMediator) {
            adapter = value.adapter

            value.layoutManager = null
            layoutManager = value.onCreateLayoutManager()
            value.layoutManager = layoutManager

            value.itemDecoration?.run {
                addItemDecoration(this)
            }
            value.snapper?.run {
                attachToRecyclerView(this@BaseRecyclerView)
            }
            value.onCreateScrollListener()?.run {
                addOnScrollListener(this)
            }
            layoutMediator = value
        }
    }

    //======================================================================
    // companion
    //======================================================================

    companion object {
        @JvmStatic
        @Deprecated("사용안함", ReplaceWith("view.layoutMediator = value"))
        @BindingAdapter("setAdapter")
        fun setAdapter(view: RecyclerView, adapter: BaseRecyclerViewAdapter) {
            view.adapter = adapter
        }

        @JvmStatic
        @Deprecated("사용안함", ReplaceWith("view.layoutMediator = value"))
        @BindingAdapter("setLayoutManager")
        fun setLayoutManager(view: RecyclerView, value: LayoutManager) {
            view.layoutManager = value
        }

        @JvmStatic
        @Deprecated("사용안함", ReplaceWith("view.layoutMediator = value"))
        @BindingAdapter("addItemDecoration")
        fun addItemDecoration(view: RecyclerView, value: ItemDecoration) {
            view.addItemDecoration(value)
        }

        @JvmStatic
        @Deprecated("사용안함", ReplaceWith("view.layoutMediator = value"))
        @BindingAdapter("setDecoration")
        fun addItemDecoration(view: BaseRecyclerView, value: Provider) {
            view.setDecoration(value)
        }
    }

    //======================================================================
    // Provider
    //======================================================================

    @Deprecated("사용안함", ReplaceWith("view.layoutMediator = value"))
    abstract class Provider {

        //======================================================================
        // Variables
        //======================================================================

        lateinit var view: () -> BaseRecyclerView

        internal var visibleUpdate: ((visible: Int) -> Unit)? = null

        internal var scrollPosition: ((position: Int, smooth: Boolean) -> Unit)? = null

        val context: Context
            get() = view().context

        val adapter: BaseRecyclerViewAdapter by lazy {
            onCreateAdapter()
        }

        val layoutManager: LayoutManager?
            get() = view().layoutManager

        val itemDecoration: ItemDecoration? by lazy {
            onCreateItemDecoration()
        }

        val snapper: SnapHelper? by lazy {
            onCreateSnapHelper()
        }

        //======================================================================
        // Abstract Methods
        //======================================================================

        protected abstract fun onCreateAdapter(): BaseRecyclerViewAdapter

        abstract fun onCreateLayoutManager(): LayoutManager

        //======================================================================
        // Public Methods
        //======================================================================

        open fun onVisible(): Int {
            return View.VISIBLE
        }

        open fun onBind() {

        }

        open fun onDataSetChange(view: BaseRecyclerView, change: Boolean) {

        }

        //======================================================================
        // Protected Methods
        //======================================================================

        protected open fun onCreateItemDecoration(): ItemDecoration? {
            return null
        }

        protected open fun onCreateSnapHelper(): SnapHelper? {
            return null
        }

        protected fun requestVisible() {
            visibleUpdate?.let {
                it(onVisible())
            }
        }

        protected fun scrollToPosition(position: Int, smooth: Boolean) {
            scrollPosition?.let {
                it(position, smooth)
            }
        }

        //======================================================================
        // Private Methods
        //======================================================================

        internal fun onBindInternal(view: BaseRecyclerView) {
            view.visibility = onVisible()
            onBind()
        }
    }

    //======================================================================
    // LayoutMediator
    //======================================================================

    abstract class LayoutMediator {

        //======================================================================
        // Variables
        //======================================================================

        val adapter: Adapter<out ViewHolder> by lazy {
            onCreateAdapter()
        }

        var layoutManager: LayoutManager? = null
            internal set

        val itemDecoration: ItemDecoration? by lazy {
            onCreateItemDecoration()
        }

        val snapper: SnapHelper? by lazy {
            onCreateSnapHelper()
        }

        //======================================================================
        // Abstract Methods
        //======================================================================

        abstract fun onCreateAdapter(): Adapter<out ViewHolder>

        abstract fun onCreateLayoutManager(): LayoutManager

        //======================================================================
        // Public Methods
        //======================================================================

        open fun onCreateItemDecoration(): ItemDecoration? {
            return null
        }

        open fun onCreateSnapHelper(): SnapHelper? {
            return null
        }

        open fun onCreateScrollListener(): OnScrollListener? {
            return null
        }

    }
}
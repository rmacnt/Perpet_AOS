package pet.perpet.framework.fragment

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewAdapter
import pet.perpet.framework.widget.recyclerview.RecyclerViewHolder
import pet.perpet.framework.widget.selector.MultiSelector

abstract class RecyclerViewPresenter(fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Variables
    //======================================================================

    val adapterProvider: BaseRecyclerViewAdapter.Provider by lazy {
        onCreateProvider()
    }

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

    protected abstract fun onCreateViewHolderSet(): ViewHolderSet?

    protected abstract fun onCreateProvider(): BaseRecyclerViewAdapter.Provider

    //======================================================================
    // Public Methods
    //======================================================================

    open fun onCreateRecyclerViewAdapter(): BaseRecyclerViewAdapter {
        val a = object : BaseRecyclerViewAdapter() {
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
        a.provider = adapterProvider
        a.setHasStableIds(true)
        return a
    }
}

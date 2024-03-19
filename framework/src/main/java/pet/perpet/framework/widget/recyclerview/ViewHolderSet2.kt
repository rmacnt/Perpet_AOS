package pet.perpet.framework.widget.recyclerview

import android.view.ViewGroup
import pet.perpet.framework.fragment.ViewHolderFactory

abstract class ViewHolderSet2<Key> {

    private val viewHolderSet: HashMap<Int, Class<out RecyclerViewHolder<*>>> = hashMapOf()

    abstract fun onDependencyViewHolder(key: Key?): Class<out RecyclerViewHolder<*>>

    fun asViewType(key: Key?): Int {
        val value = onDependencyViewHolder(key)
        val viewType = value.hashCode()
        synchronized(viewHolderSet) {
            viewHolderSet[viewType] = value
        }
        return viewType
    }

    fun createViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewHolder<*>? {
        synchronized(viewHolderSet) {
            val holderC = viewHolderSet[viewType]
            return ViewHolderFactory.create(holderC!!, parent)
        }
    }
}
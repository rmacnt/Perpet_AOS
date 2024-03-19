package pet.perpet.framework.fragment

import pet.perpet.framework.widget.recyclerview.RecyclerViewHolder


abstract class ViewHolderSet {

    abstract val viewHolderSet: Array<Class<out RecyclerViewHolder<*>>>

    abstract fun asViewType(position: Int): Int

    fun findViewHolderClass(id: Int): Class<*> {
        for (item in viewHolderSet) {
            if (item.hashCode() == id) {
                return item
            }
        }
        return Object::class.java
    }

    companion object {

        @JvmStatic
        val EMPTY = object : ViewHolderSet() {
            override val viewHolderSet: Array<Class<out RecyclerViewHolder<*>>>
                get() = arrayOf(RecyclerViewHolder::class.java)

            override fun asViewType(position: Int): Int {
                return RecyclerViewHolder::class.java.hashCode()
            }
        }
    }
}
package pet.perpet.framework.widget.recyclerview

interface OnSelfRemoveViewHolderListener {
    fun onSelfRemoveViewHolder(holder: BaseRecyclerViewHolder<*>): Int
}
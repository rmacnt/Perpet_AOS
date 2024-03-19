package pet.perpet.equal.presentation.supplement.differ

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import pet.perpet.domain.model.medical.Package
import pet.perpet.equal.presentation.base.factory.BaseRecyclerViewAdapter
import pet.perpet.equal.presentation.examination.viewholder.PackageListItemViewHolder
import pet.perpet.equal.presentation.supplement.viewholder.PackageListItemInfoViewHolder
import pet.perpet.framework.util.Logger
import pet.perpet.framework.widget.recyclerview.RecyclerViewHolder
import pet.perpet.framework.widget.recyclerview.ViewHolderSet2

class PackageInfoItemListDiffer {

    val adapter: RecyclerView.Adapter<*> by lazy {
        object : BaseRecyclerViewAdapter<Any, RecyclerViewHolder<Any>>() {
            override val holderSet: ViewHolderSet2<Any> by lazy {
                object : ViewHolderSet2<Any>() {
                    override fun onDependencyViewHolder(key: Any?): Class<out RecyclerViewHolder<*>> {
                        if (key is String) {
                            return PackageListItemInfoViewHolder::class.java
                        }
                        return PackageListItemInfoViewHolder::class.java
                    }

                }
            }

            override fun getSupportItem(position: Int): Any? {
                return getRawItem(position)
            }

            override fun getItemId(position: Int): Long {
                return (Integer.MIN_VALUE + position).toLong()
            }

            override fun getItemCount(): Int {
                return itemSize
            }
        }.apply {
            setHasStableIds(true)
        }
    }

    private val dataSet = mutableListOf<Any>()

    val itemSize: Int
        get() = dataSet.size

    //======================================================================
    // Public Methods
    //======================================================================

    fun getRawItem(position: Int): Any? {
        try {
            return dataSet[position]
        } catch (e: Exception) {
            Logger.printStackTrace(e)
        }
        return null
    }

    fun allList(newList: List<Any>?) {
        Logger.w(
            "ItemListDiffer",
            "allList:${itemSize} newList:${newList?.size}"
        )
        if (newList != null) {
            calDiff(newList)
            dataSet.clear()
            dataSet.addAll(newList)
            adapter?.notifyDataSetChanged()
        }
    }

    fun clearList() {
        dataSet.clear()
        adapter.notifyDataSetChanged()
    }

    fun snapList(): List<Package> {
        val newList = mutableListOf<Package>()
        newList.addAll(dataSet.filterIsInstance<Package>())
        return newList
    }

    private fun calDiff(newTiles: List<Any>) {
        val packageInfoItemListDifferCallback = PackageInfoItemListDifferCallback(dataSet, newTiles)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(packageInfoItemListDifferCallback)
        diffResult.dispatchUpdatesTo(createListUpdateCallback())
    }

    //======================================================================
    // Private Methods
    //======================================================================

    private fun createListUpdateCallback(): ListUpdateCallback {
        return object : ListUpdateCallback {
            override fun onChanged(position: Int, count: Int, payload: Any?) {
                Logger.w(
                    "ItemListDiffer",
                    "itemCount:${itemSize} onChanged > position : $position, count : $count, payload : $payload"
                )
                adapter?.notifyItemRangeChanged(position, count, payload)
            }

            override fun onMoved(fromPosition: Int, toPosition: Int) {
                Logger.w(
                    "ItemListDiffer",
                    "itemCount:${itemSize} onMoved > fromPosition : $fromPosition, toPosition : $toPosition"
                )
                adapter?.notifyItemMoved(fromPosition, toPosition)
            }

            override fun onInserted(position: Int, count: Int) {
                Logger.w(
                    "ItemListDiffer",
                    "itemCount:${itemSize} onInserted > position : $position, count : $count"
                )
                adapter?.notifyItemRangeInserted(position, count)
            }

            override fun onRemoved(position: Int, count: Int) {
                Logger.w(
                    "ItemListDiffer",
                    "itemCount:${itemSize} onRemoved > position : $position, count : $count"
                )
                adapter?.notifyItemRangeRemoved(position, count)
            }
        }
    }

    private fun createItemCallback(): DiffUtil.ItemCallback<Package> {
        return object : DiffUtil.ItemCallback<Package>() {
            override fun areItemsTheSame(
                oldItem: Package,
                newItem: Package
            ): Boolean {
                Logger.w(
                    "ItemListDiffer",
                    "\nareItemsTheSame[${oldItem.hashCode()}]:${oldItem}\nnewItem[${newItem.hashCode()}]:$newItem"
                )
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Package,
                newItem: Package
            ): Boolean {
                Logger.w(
                    "ItemListDiffer",
                    "\nareContentsTheSame[${oldItem.hashCode()}]:${oldItem}\nnewItem[${newItem.hashCode()}]:$newItem"
                )
                return oldItem == newItem
            }
        }
    }
}

class PackageInfoItemListDifferCallback(
    private val oldTiles: List<Any>,
    private val newTiles: List<Any>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTiles[oldItemPosition] == newTiles[newItemPosition]
    }

    override fun getOldListSize(): Int {
        return oldTiles.size
    }

    override fun getNewListSize(): Int {
        return newTiles.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTiles[oldItemPosition] == newTiles[newItemPosition]
    }
}

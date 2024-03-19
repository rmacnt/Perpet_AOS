package pet.perpet.equal.presentation.home.differ

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import pet.perpet.domain.model.pet.Pet
import pet.perpet.equal.presentation.home.viewholder.MainItemViewHolder
import pet.perpet.framework.util.Logger
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewAdapter
import pet.perpet.framework.widget.recyclerview.RecyclerViewHolder

class MainPetListDiffer {

    val adapter: BaseRecyclerViewAdapter by lazy {
        object : BaseRecyclerViewAdapter(provider) {
            override fun onCreateItemViewHolder(
                parent: ViewGroup?,
                viewType: Int,
            ): RecyclerViewHolder<*> {
                return MainItemViewHolder(parent)
            }
        }.apply {
            setHasStableIds(true)
        }
    }

    val provider: BaseRecyclerViewAdapter.ArgumentsProvider by lazy {
        object : BaseRecyclerViewAdapter.ArgumentsProvider {
            override fun getItemHeaderCount(): Int = 0

            override fun getSupportItemCount(): Int {
                return dataSet.size
            }

            override fun getSupportItemViewType(position: Int): Int {
                return -1
            }

            override fun getSupportItem(viewType: Int, position: Int): Any? {
                return dataSet[position]
            }

            override fun onBindArguments(viewType: Int, arguments: Bundle?) {

            }
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

    fun add(newList: Any?) {
        newList?.let {
            dataSet.add(newList)
            adapter?.notifyDataSetChanged()
        }

    }


    fun clearList() {
        dataSet.clear()
        adapter.notifyDataSetChanged()
    }

    fun allGetList() : ArrayList<Any?>{
        val item = ArrayList<Any?>()
        dataSet.forEach {
            item.add(it)
        }
        return item
    }


    fun snapList(): List<Pet> {
        val newList = mutableListOf<Pet>()
        newList.addAll(dataSet.filterIsInstance<Pet>())
        return newList
    }

    private fun calDiff(newTiles: List<Any>) {
        val mainPetListDifferCallback = MainPetListDifferCallback(dataSet, newTiles)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(mainPetListDifferCallback)
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

    private fun createItemCallback(): DiffUtil.ItemCallback<Pet> {
        return object : DiffUtil.ItemCallback<Pet>() {
            override fun areItemsTheSame(
                oldItem: Pet,
                newItem: Pet
            ): Boolean {
                Logger.w(
                    "ItemListDiffer",
                    "\nareItemsTheSame[${oldItem.hashCode()}]:${oldItem}\nnewItem[${newItem.hashCode()}]:$newItem"
                )
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Pet,
                newItem: Pet
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

class MainPetListDifferCallback(
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

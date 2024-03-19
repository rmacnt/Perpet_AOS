package pet.perpet.equal.presentation.sign.differ

import android.os.Bundle
import android.view.ViewGroup
import pet.perpet.domain.model.profile.Disease
import pet.perpet.equal.presentation.sign.viewholder.ItemDiseaseCommentViewHolder
import pet.perpet.framework.util.Logger
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewAdapter
import pet.perpet.framework.widget.recyclerview.RecyclerViewHolder

class DiseaseCommentItemListDiffer {

    val adapter: BaseRecyclerViewAdapter by lazy {
        object : BaseRecyclerViewAdapter(provider) {
            override fun onCreateItemViewHolder(
                parent: ViewGroup?,
                viewType: Int,
            ): RecyclerViewHolder<*> {
                return ItemDiseaseCommentViewHolder(parent)
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

    fun allGetList(): ArrayList<Any?> {
        val item = ArrayList<Any?>()
        dataSet.forEach {
            item.add(it)
        }
        return item
    }

    fun allList(): MutableList<Any> {
        return dataSet
    }


    fun snapList(): List<Disease> {
        val newList = mutableListOf<Disease>()
        newList.addAll(dataSet.filterIsInstance<Disease>())
        return newList
    }

    //======================================================================
    // Private Methods
    //======================================================================

}
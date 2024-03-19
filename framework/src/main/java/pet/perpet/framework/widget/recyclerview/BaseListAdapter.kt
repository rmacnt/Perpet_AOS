package pet.perpet.framework.widget.recyclerview

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import pet.perpet.framework.nonnull
import pet.perpet.framework.removeIndex
import pet.perpet.framework.replace
import pet.perpet.framework.util.Logger

abstract class BaseListAdapter : BaseRecyclerViewAdapter() {

    //======================================================================
    // Variables
    //======================================================================

    private val listProvider: ListProvider by lazy {
        onCreateListProvider()
    }

    private val helper: AsyncListDiffer<Any>

    private val helperItemCount: Int
        get() = helper.currentList.size.nonnull()

    init {
        provider = createProvider()
        helper = AsyncListDiffer(
            object : ListUpdateCallback {
                override fun onChanged(position: Int, count: Int, payload: Any?) {
                    log(
                        "onChanged > position : $position, count : $count, payload : $payload"
                    )
                    this@BaseListAdapter.notifyItemRangeChanged(position, count, payload)
                }

                override fun onMoved(fromPosition: Int, toPosition: Int) {
                    log(
                        "onMoved > fromPosition : $fromPosition, toPosition : $toPosition"
                    )
                    this@BaseListAdapter.notifyItemMoved(fromPosition, toPosition)
                }

                override fun onInserted(position: Int, count: Int) {
                    log(
                        "onInserted > position : $position, count : $count, itemCount : ${this@BaseListAdapter.helperItemCount}"
                    )
                    if (position < itemHeaderCount) {
                        this@BaseListAdapter.notifyDataSetChanged()
                    } else {
                        this@BaseListAdapter.notifyItemRangeInserted(position, count)
                    }
                }

                override fun onRemoved(position: Int, count: Int) {
                    log(
                        "onRemoved > position : $position, count : $count, itemCount : ${this@BaseListAdapter.helperItemCount}"
                    )
                    this@BaseListAdapter.notifyItemRangeRemoved(position, count)
                }
            },
            AsyncDifferConfig.Builder<Any>(object : DiffUtil.ItemCallback<Any>() {
                override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
                    log(
                        "areItemsTheSame > oldItem : ${oldItem.hashCode()}, newItem : ${newItem.hashCode()}"
                    )
                    return oldItem == newItem
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
                    log("areContentsTheSame > oldItem : ${oldItem.hashCode()}, newItem : ${newItem.hashCode()}")
                    return oldItem == newItem
                }

            }).build()
        )
    }

    //======================================================================
    // Abstract Methods
    //======================================================================

    abstract fun onCreateListProvider(): ListProvider

    abstract fun onCreateDiffCallback(): DiffUtil.ItemCallback<Any>

    //======================================================================
    // Private Methods
    //======================================================================

    private fun createProvider(): ArgumentsProvider {
        return object : ArgumentsProvider {
            override fun getItemHeaderCount(): Int {
                return listProvider.itemHeaderCount
            }

            override fun getSupportItemCount(): Int {
                return helper.currentList.size
            }

            override fun onBindArguments(viewType: Int, arguments: Bundle?) {
                listProvider.onBindArguments(viewType, arguments)
            }

            override fun getSupportItem(viewType: Int, position: Int): Any? {
                val headerCount = this@BaseListAdapter.itemHeaderCount
                if (position < headerCount) {
                    return listProvider.getHeaderItem(viewType, position)
                }

                val itemSize = helper.currentList.size
                val rePosition = position - headerCount
                val valid = rePosition.isValid() && itemSize.isValid()
                return if (valid == true && rePosition < itemSize) {
                    helper.currentList[rePosition]
                } else {
                    null
                }
            }

            override fun getSupportItemViewType(position: Int): Int {
                return listProvider.getSupportItemViewType(position)
            }
        }
    }

    //======================================================================
    // Public Methods
    //======================================================================

    fun submitList(list: List<Any>?) {
        helper.submitList(list)
    }

    fun remove(any: Any): Boolean {
        val temps = arrayListOf<Any>()
        temps.addAll(helper.currentList)
        val index = temps.remove(any)
        helper.submitList(temps)
        return index
    }

    fun remove(compare: (value: Any) -> Boolean): Int {
        val temps = arrayListOf<Any>()
        temps.addAll(helper.currentList)
        val index = temps.removeIndex(compare)
        helper.submitList(temps)
        return index
    }

    fun add(index: Int, value: Any) {
        val temps = arrayListOf<Any>()
        temps.addAll(helper.currentList)
        temps.add(index, value)
        helper.submitList(null)
        helper.submitList(temps)
    }

    fun addAll(value: List<Any>?) {
        helper.submitList(null)
        if (value?.isNotEmpty() == true) {
            helper.submitList(value)
        }
    }

    fun addAll(index: Int, value: List<Any>) {
        val temps = arrayListOf<Any>()
        temps.addAll(helper.currentList)
        temps.addAll(index, value)
        helper.submitList(null)
        helper.submitList(temps)
    }

    fun replace(compare: (value: Any) -> Boolean, dat: Any) {
        val temps = arrayListOf<Any>()
        temps.addAll(helper.currentList)
        temps.replace(compare, dat)
        helper.submitList(null)
        helper.submitList(temps)
    }

    fun getRawItem(pos: Int): Any? {
        try {
            val headerCount = itemHeaderCount
            val rePos = pos - headerCount
            val itemCount = helper.currentList.size.nonnull(-1)
            return if (rePos < itemCount) {
                helper.currentList[pos]
            } else {
                null
            }
        } catch (e: Exception) {
            // Noting
        }
        return null
    }

    fun readItemList(): MutableList<Any> {
        return helper.currentList
    }

    fun getRawItemSize(): Int {
        return helper.currentList.size.nonnull(-1)
    }

    //======================================================================
    // ListProvider
    //======================================================================

    abstract class ListProvider : ArgumentsProvider {
        open fun getHeaderItem(viewType: Int, position: Int): Any? {
            return null
        }

        final override fun getSupportItemCount(): Int {
            // Noting
            return -1
        }

        final override fun getSupportItem(viewType: Int, position: Int): Any? {
            // Noting
            return null
        }
    }

    //======================================================================
    // companion
    //======================================================================

    companion object {
        private const val DIFF_VIEW_HOLDER = "DIFF_VIEW_HOLDER"

        fun log(message: String) {
            Logger.w(
                DIFF_VIEW_HOLDER, message
            )
        }

        fun Int.isValid(min: Int = 0): Boolean {
            return this > min
        }

        fun RecyclerViewAdapter.asAdapter(): BaseListAdapter? {
            return if (this is BaseListAdapter) {
                this
            } else {
                null
            }
        }
    }
}

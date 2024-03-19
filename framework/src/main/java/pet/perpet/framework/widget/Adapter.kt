package pet.perpet.framework.widget


import android.os.Bundle
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import pet.perpet.framework.fragment.ViewBindingMapper
import pet.perpet.framework.widget.recyclerview.AdapterUtil
import pet.perpet.framework.widget.recyclerview.RecyclerViewHolder
import pet.perpet.framework.widget.recyclerview.ViewHolderSet2
import pet.perpet.framework.widget.utils.invokeDeclaredMethodsAnnotation

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@kotlin.annotation.Target(AnnotationTarget.FUNCTION)
annotation class OnBindArguments

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@kotlin.annotation.Target(AnnotationTarget.FUNCTION)
annotation class GetLifeCycleOwner

abstract class BaseRecyclerViewAdapter<Item : Any, VH : RecyclerViewHolder<Item>> :
    RecyclerView.Adapter<VH>() {

    abstract val holderSet: ViewHolderSet2<Item>

    private val bundle: Bundle = Bundle()

    abstract fun getSupportItem(position: Int): Item?

    override fun onBindViewHolder(holder: VH, position: Int) {
        try {
            this.invokeDeclaredMethodsAnnotation(OnBindArguments::class.java, bundle)
            holder.onBindArguments(bundle)
        } catch (e: java.lang.Exception) {
        }
        try {
            val item = getSupportItem(position)
            holder.isPayloadHolderChange = false
            holder.onBindViewHolder(item)
        } catch (e: java.lang.Exception) {
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) {
        try {
            this.invokeDeclaredMethodsAnnotation(OnBindArguments::class.java, bundle)
            holder.onBindArguments(bundle)
        } catch (e: java.lang.Exception) {
        }
        try {
            val item = getSupportItem(position)
            val payloadChange = payloads.find {
                it == "update"
            } != null
            holder.isPayloadHolderChange = payloadChange
            holder.onBindViewHolder(item)
            ViewBindingMapper.bind(holder)
        } catch (e: java.lang.Exception) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val holder = holderSet.createViewHolder(parent, viewType) as VH
        try {
            val owner = this.invokeDeclaredMethodsAnnotation(GetLifeCycleOwner::class.java)
            holder.onBindingLifeCycleScope(owner as LifecycleOwner?)
        } catch (e: java.lang.Exception) {
        }
        return holder
    }

    override fun getItemViewType(position: Int): Int {
        try {
            return holderSet.asViewType(getSupportItem(position))
        } catch (e: Exception) {
        }
        return super.getItemViewType(position)
    }

    override fun onViewRecycled(holder: VH) {
        super.onViewRecycled(holder)
        holder.onViewRecycled()
        ViewBindingMapper.unbind(holder)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        AdapterUtil.releaseRecyclerViewHolder(recyclerView)
    }

    override fun onFailedToRecycleView(holder: VH): Boolean {
        return super.onFailedToRecycleView(holder)
        lw("onFailedToRecycleView >> ${holder}")
    }

    override fun onViewAttachedToWindow(holder: VH) {
        super.onViewAttachedToWindow(holder)
        holder.onViewAttachedToWindow()
    }

    override fun onViewDetachedFromWindow(holder: VH) {
        super.onViewDetachedFromWindow(holder)
        holder.onViewDetachedFromWindow()
    }

    val logger = true

    fun lw(message: String) {
    }
}

abstract class DefaultRecyclerViewAdapter : BaseRecyclerViewAdapter<Any, RecyclerViewHolder<Any>>()


fun RecyclerView.Adapter<out RecyclerView.ViewHolder>.diff(
    oldItems: List<Any>,
    newItems: List<Any>,
    diff: DiffUtil.Callback? = null
) {

    val logger = false

    fun lw(message: String) {
    }

    fun createListUpdateCallback(): ListUpdateCallback {
        return object : ListUpdateCallback {
            override fun onChanged(position: Int, count: Int, payload: Any?) {
                lw(
                    "onChanged > position : ${position}, count : ${count}, payload : ${payload}"
                )
                this@diff.notifyItemRangeChanged(position, count, payload)
            }

            override fun onMoved(fromPosition: Int, toPosition: Int) {
                lw(
                    "onMoved > fromPosition : ${fromPosition}, toPosition : $toPosition"
                )
                this@diff.notifyItemMoved(fromPosition, toPosition)
            }

            override fun onInserted(position: Int, count: Int) {
                val itemCount = this@diff.itemCount
                lw(
                    "onInserted > position : ${position}, count : $count, itemCount : ${itemCount}"
                )
                this@diff.notifyItemRangeInserted(position, count)
            }

            override fun onRemoved(position: Int, count: Int) {
                val itemCount = this@diff.itemCount
                lw(
                    "onRemoved > position : ${position}, count : $count, itemCount : ${itemCount}"
                )
                this@diff.notifyItemRangeRemoved(position, count)
            }
        }
    }

    val tileDiffUtilCallback =
        diff ?: DefaultDffUtilCallback(oldItems, newItems)
    val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(tileDiffUtilCallback)
    diffResult.dispatchUpdatesTo(createListUpdateCallback())
}

//======================================================================
// TileDiffUtilCallback
//======================================================================

internal class DefaultDffUtilCallback(
    private val oldTiles: List<Any>,
    private val newTiles: List<Any>
) : DiffUtil.Callback() {

    private val logger = false

    override fun getOldListSize(): Int {
        return oldTiles.size
    }

    override fun getNewListSize(): Int {
        return newTiles.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldTiles[oldItemPosition]
        val new = newTiles[newItemPosition]
        val areItemsTheSame = old == new
        lw(
            "areItemsTheSame $areItemsTheSame > old : ${old.hashCode()}, new : ${new.hashCode()}"
        )
        return areItemsTheSame
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldTiles[oldItemPosition]
        val new = newTiles[newItemPosition]
        val areContentsTheSame = old == new
        lw(
            "areContentsTheSame $areContentsTheSame > old : ${old.hashCode()}, new : ${new.hashCode()}"
        )
        return areContentsTheSame
    }

    fun lw(message: String) {
    }
}
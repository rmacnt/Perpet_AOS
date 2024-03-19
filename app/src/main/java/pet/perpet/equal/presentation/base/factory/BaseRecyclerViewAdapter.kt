package pet.perpet.equal.presentation.base.factory

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pet.perpet.framework.fragment.ViewBindingMapper
import pet.perpet.framework.util.Logger
import pet.perpet.framework.widget.recyclerview.AdapterUtil
import pet.perpet.framework.widget.recyclerview.RecyclerViewHolder
import pet.perpet.framework.widget.recyclerview.ViewHolderSet2

abstract class BaseRecyclerViewAdapter<Item : Any, VH : RecyclerViewHolder<Item>> :
    RecyclerView.Adapter<VH>() {

    abstract val holderSet: ViewHolderSet2<Item>

    abstract fun getSupportItem(position: Int): Item?

    override fun onBindViewHolder(holder: VH, position: Int) {
        try {
            val item = getSupportItem(position)
            holder.onBindViewHolder(item)
        } catch (e: java.lang.Exception) {
            Logger.printStackTrace(e)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return holderSet.createViewHolder(parent, viewType) as VH
    }

    override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        try {
            val item = getSupportItem(position)
            holder.onBindViewHolder(item)
            ViewBindingMapper.bind(holder)
        } catch (e: java.lang.Exception) {
            Logger.printStackTrace(e)
        }
    }

    override fun getItemViewType(position: Int): Int {
        try {
            return holderSet.asViewType(getSupportItem(position))
        } catch (e: Exception) {
            Logger.printStackTrace(e)
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
        Logger.w("onDetachedFromRecyclerView", "onDetachedFromRecyclerView")
    }

    override fun onFailedToRecycleView(holder: VH): Boolean {
        Logger.w("onFailedToRecycleView", "onFailedToRecycleView")
        return super.onFailedToRecycleView(holder)
    }

    override fun onViewAttachedToWindow(holder: VH) {
        super.onViewAttachedToWindow(holder)
        Logger.w("onViewAttachedToWindow", "onViewAttachedToWindow")
    }

    override fun onViewDetachedFromWindow(holder: VH) {
        super.onViewDetachedFromWindow(holder)
        Logger.w("onViewDetachedFromWindow", "onViewDetachedFromWindow")
    }
}
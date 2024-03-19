package pet.perpet.framework.fragment

import android.view.ViewGroup
import pet.perpet.framework.util.Config
import pet.perpet.framework.util.Logger
import pet.perpet.framework.widget.recyclerview.RecyclerViewHolder
import pet.perpet.framework.widget.recyclerview.holder.SelectorViewHolder
import pet.perpet.framework.widget.selector.MultiSelector

object ViewHolderFactory {

    fun create(
        viewHolderClass: Class<*>,
        viewGroup: ViewGroup,
        selector: MultiSelector?
    ): RecyclerViewHolder<*>? {
        if (Config.isLogEnable()) {
            Logger.w(
                "ViewHolderFactory",
                "createViewHolder > name : ${viewHolderClass.name}"
            )
        }
        val holder = create(
            viewHolderClass,
            viewGroup
        )
        if (holder != null && holder is SelectorViewHolder<*>) {
            holder.setMultiSelector(selector)
        }
        return holder
    }

    fun create(
        viewHolderClass: Class<*>,
        viewGroup: ViewGroup
    ): RecyclerViewHolder<*>? {
        try {
            return viewHolderClass.getDeclaredConstructor(
                ViewGroup::class.java
            ).newInstance(
                viewGroup
            ) as RecyclerViewHolder<*>
        } catch (e: Exception) {
            if (Config.isLogEnable()) {
                Logger.e(
                    "ViewHolderFactory",
                    "createViewHolder > name : ${viewHolderClass.name}"
                )
            }
            e.printStackTrace()
        }
        return null
    }
}
package pet.perpet.framework.widget.recyclerview

import android.os.Bundle

interface OnViewHolderEventListener {

    fun onRecyclerViewHolderEvent(holder: BaseRecyclerViewHolder<*>, id: Int, bundle: Bundle)
}
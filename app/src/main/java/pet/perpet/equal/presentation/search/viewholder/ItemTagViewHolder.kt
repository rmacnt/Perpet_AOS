package pet.perpet.equal.presentation.search.viewholder

import android.view.ViewGroup
import pet.perpet.domain.model.search.Tag
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSupplementTagBinding
import pet.perpet.equal.presentation.search.viewmodel.ItemTagViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder
import pet.perpet.framework.widget.recyclerview.RecyclerViewHolder

class ItemTagViewHolder  (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<Tag>(viewGroup, R.layout.item_supplement_tag) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemSupplementTagBinding = ItemSupplementTagBinding.bind(itemView)

    val viewmodel: ItemTagViewModel = ItemTagViewModel()

    init {
        viewmodel.notify {
            sendEvent(hashCode())
        }
    }


    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: Tag?) {
        viewmodel.model = t
        binding.model = viewmodel
    }
}
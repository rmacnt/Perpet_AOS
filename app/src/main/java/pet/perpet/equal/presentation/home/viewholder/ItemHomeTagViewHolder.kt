package pet.perpet.equal.presentation.home.viewholder

import android.view.ViewGroup
import pet.perpet.domain.model.main.HomeTag
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemHomeTagBinding
import pet.perpet.equal.presentation.home.viewmodel.ItemHomeTagViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class ItemHomeTagViewHolder  (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<HomeTag>(viewGroup, R.layout.item_home_tag) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemHomeTagBinding = ItemHomeTagBinding.bind(itemView)

    val viewmodel: ItemHomeTagViewModel = ItemHomeTagViewModel()

    init {
        viewmodel.notify {
            sendEvent(hashCode())
        }
    }

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: HomeTag?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }
}
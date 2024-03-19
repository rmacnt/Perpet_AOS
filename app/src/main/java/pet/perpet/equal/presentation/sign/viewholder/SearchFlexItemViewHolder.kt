package pet.perpet.equal.presentation.sign.viewholder

import android.util.Log
import android.view.ViewGroup
import pet.perpet.domain.model.profile.Allergy
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSearchFlexBinding
import pet.perpet.equal.presentation.sign.viewmodel.SearchFlexItemViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class SearchFlexItemViewHolder(viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<Allergy>(viewGroup, R.layout.item_search_flex) {

    //======================================================================
    // Variables
    //======================================================================

    val viewmodel: SearchFlexItemViewModel = object : SearchFlexItemViewModel() {

    }

    val binding: ItemSearchFlexBinding = ItemSearchFlexBinding.bind(itemView)

    init {
        viewmodel.notify {
            sendEvent(hashCode())
        }
    }

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: Allergy?) {
        viewmodel.model = t
        binding.model = viewmodel
    }
}
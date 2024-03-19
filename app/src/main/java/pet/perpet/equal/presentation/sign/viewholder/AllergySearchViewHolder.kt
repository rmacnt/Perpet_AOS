package pet.perpet.equal.presentation.sign.viewholder

import android.view.ViewGroup
import pet.perpet.domain.model.profile.Allergy
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSearchAllergyBinding
import pet.perpet.equal.presentation.sign.viewmodel.AllergySearchViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder
import pet.perpet.framework.widget.recyclerview.RecyclerViewHolder

class AllergySearchViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<Allergy>(viewGroup, R.layout.item_search_allergy) {

    //======================================================================
    // Variables
    //======================================================================

    val viewmodel: AllergySearchViewModel = object : AllergySearchViewModel() {

    }

    val binding: ItemSearchAllergyBinding = ItemSearchAllergyBinding.bind(itemView)

    init {
        viewmodel.notify {
            viewmodel.model = it
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
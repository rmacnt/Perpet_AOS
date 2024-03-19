package pet.perpet.equal.presentation.sign.viewholder

import android.view.ViewGroup
import pet.perpet.domain.model.profile.Disease
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSearchDiseaseBinding
import pet.perpet.equal.presentation.sign.viewmodel.DiseaseSearchViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class DiseaseSearchViewHolder  (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<Disease>(viewGroup, R.layout.item_search_disease) {

    //======================================================================
    // Variables
    //======================================================================

    val viewmodel: DiseaseSearchViewModel = object : DiseaseSearchViewModel() {

    }

    val binding: ItemSearchDiseaseBinding = ItemSearchDiseaseBinding.bind(itemView)

    init {
        viewmodel.notify {
            viewmodel.model = it
            sendEvent(hashCode())
        }
    }

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: Disease?) {
        viewmodel.model = t
        binding.model = viewmodel
    }
}
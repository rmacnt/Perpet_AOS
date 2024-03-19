package pet.perpet.equal.presentation.sign.viewholder

import android.view.ViewGroup
import pet.perpet.domain.model.profile.Disease
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemDiseaseSearchFlexBinding
import pet.perpet.equal.presentation.sign.viewmodel.DiseaseSearchFlexItemViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class DiseaseSearchFlexItemViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<Disease>(viewGroup, R.layout.item_disease_search_flex) {

    //======================================================================
    // Variables
    //======================================================================

    val viewmodel: DiseaseSearchFlexItemViewModel = object : DiseaseSearchFlexItemViewModel() {

    }

    val binding: ItemDiseaseSearchFlexBinding = ItemDiseaseSearchFlexBinding.bind(itemView)

    init {
        viewmodel.notify {
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
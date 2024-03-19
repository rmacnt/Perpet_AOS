package pet.perpet.equal.presentation.sign.viewholder

import android.view.ViewGroup
import pet.perpet.domain.model.profile.Disease
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemDiseaseFlexboxBinding
import pet.perpet.equal.presentation.sign.viewmodel.DiseaseViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder
import pet.perpet.framework.widget.recyclerview.RecyclerViewHolder

class DiseaseViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<Disease>(viewGroup, R.layout.item_disease_flexbox) {

    //======================================================================
    // Variables
    //======================================================================

    val viewmodel: DiseaseViewModel = object : DiseaseViewModel() {

    }

    val binding: ItemDiseaseFlexboxBinding = ItemDiseaseFlexboxBinding.bind(itemView)

    init {
        viewmodel.notify {
            sendEvent(hashCode())
        }
    }

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: Disease?) {
        viewmodel.bind(t)
        binding.model = viewmodel
    }
}
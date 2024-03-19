package pet.perpet.equal.presentation.sign.viewholder

import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSignPetDiseaseTherapyBinding
import pet.perpet.equal.presentation.sign.model.PetDiseaseTherapy
import pet.perpet.equal.presentation.sign.viewmodel.PetDiseaseTherapyViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class PetDiseaseTherapyViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<PetDiseaseTherapy>(
        viewGroup,
        R.layout.item_sign_pet_disease_therapy
    ) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemSignPetDiseaseTherapyBinding =
        ItemSignPetDiseaseTherapyBinding.bind(itemView)

    val viewmodel: PetDiseaseTherapyViewModel = PetDiseaseTherapyViewModel()

    init {

        viewmodel.notifyAsk {
            item.petAsk = it
            binding.model = viewmodel
        }

        viewmodel.notify {
            item.error = false
            if (item.diseaseTreatCode == it.toInt()) {
                item.diseaseTreatCode = -1
            } else {
                item.diseaseTreatCode = it.toInt()
            }
            viewmodel.model = item
            binding.model = viewmodel
            sendEvent(hashCode())
        }
    }


    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: PetDiseaseTherapy?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }

}